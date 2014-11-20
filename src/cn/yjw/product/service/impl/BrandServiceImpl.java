package cn.yjw.product.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.product.bean.Brand;
import cn.yjw.product.bean.Product;
import cn.yjw.product.bean.ProductCategory;
import cn.yjw.product.dao.BrandDao;
import cn.yjw.product.dao.ProductCategoryDao;
import cn.yjw.product.dao.ProductDao;
import cn.yjw.product.service.BrandService;
import cn.yjw.utils.page.Page;

/**
 * @author yangjunwei
 */
public class BrandServiceImpl implements BrandService {
	
	private SessionFactory sessionFactory;
	
	private BrandDao brandDao;
	
	private ProductDao productDao;
	
	private ProductCategoryDao pcDao;
	
	public BrandDao getBrandDao() {
		return brandDao;
	}
	public void setBrandDao(BrandDao brandDao) {
		this.brandDao = brandDao;
	}
	
	public ProductDao getProductDao() {
		return productDao;
	}
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	public ProductCategoryDao getPcDao() {
		return pcDao;
	}
	public void setPcDao(ProductCategoryDao pcDao) {
		this.pcDao = pcDao;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 保存商品品牌信息
	 */
	public void saveBrand(Brand brand) {
		
		// 获取数据库回话对象
		Session sess = sessionFactory.getCurrentSession();
		
		// 设置品牌对象的修改时间
		Date curDate = new Date();
		brand.setModifyTime(curDate);
		
		// 设置品牌对象的创建时间和logo，如果客户端没有更改logo，则使用原来的
		if (brand.getId() != null) {
			Brand brandTmp = brandDao.getBrandById(brand.getId());
			brand.setCreateTime(brandTmp.getCreateTime());
			if(brand.getLogo() == null) {
				brand.setLogo(brandTmp.getLogo());
			}
			// 清除Session缓存中的brand对象
			sess.evict(brandTmp);
			
		} else {
			brand.setCreateTime(curDate);
		}
		
		brandDao.saveBrand(brand);
	}
	
	/**
	 * 按指定条件分页查询品牌信息
	 */
	public Page<Brand> getBrandsByCondition(Map<String, Object> condition) {
		
		Integer pageSize = (Integer) condition.get("pageSize");
		Integer pageNumber = (Integer) condition.get("pageNumber");
		
		// 初始化品牌分页对象
		Page<Brand> brandPage = new Page<Brand>(pageSize, pageNumber);
		
		// 设置分页对象的记录总数
		int totalCount = brandDao.getBrandCountByCondition(condition);
		brandPage.setTotalCount(totalCount);
		
		// 设置分页对象的请求页码
		if(brandPage.getPageNumber() < 1){
			brandPage.setPageNumber(1);
		} else if(brandPage.getPageNumber() > brandPage.getTotalPage()){
			brandPage.setPageNumber(brandPage.getTotalPage());
		}
		// 重新设置查询条件
		condition.remove("pageNumber");
		condition.put("startIndex", brandPage.getStartIndex());
		// 根据指定条件分页查询品牌信息
		List<Brand> brands = brandDao.getBrandsByCondition(condition);
		brandPage.setList(brands);
		
		return brandPage;
	}
	
	/**
	 * 根据ID查询品牌信息
	 */
	public Brand getBrandById(Long id) {
		
		Brand brand = brandDao.getBrandById(id);
		
		return brand;
	}
	
	/**
	 * 根据ID集合批量删除品牌信息
	 */
	public void deleteBrandsByIds(Long[] ids) {
		
		// 查询待删除的品牌对象
		List<Brand> brands = brandDao.getBrandsByIds(ids);
		
		// 删除品牌对象
		for(Brand brand : brands) {
			deleteBrand(brand);
		}
	}
	
	/**
	 * 删除品牌信息
	 */
	public void deleteBrand(Brand brand) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 解除品牌与商品的关联关系
		List<Product> products = brand.getProducts();
		for(Product product : products) {
			product.setBrand(null);
			productDao.saveProduct(product);
		}
		sess.flush();
		
		// 解除品牌与商品分类的关联关系
		List<ProductCategory> pcs = brand.getPcs();
		for(ProductCategory pc : pcs) {
			pc.getBrands().remove(brand);
			pcDao.saveProductCategory(pc);
		}
		sess.flush();
		
		// 删除品牌对象
		brandDao.deleteBrand(brand);
		
	}
	
	/**
	 * 获取所有品牌信息
	 */
	public List<Brand> getAllBrands() {
		
		List<Brand> brands = brandDao.getAllBrands();
		
		return brands;
	}
	
}
