package cn.yjw.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.product.bean.Attribute;
import cn.yjw.product.bean.AttributeOption;
import cn.yjw.product.bean.Parameter;
import cn.yjw.product.bean.ParameterItem;
import cn.yjw.product.bean.Product;
import cn.yjw.product.bean.ProductImage;
import cn.yjw.product.bean.ProductParamItemValue;
import cn.yjw.product.bean.ProductSub;
import cn.yjw.product.bean.Specification;
import cn.yjw.product.dao.ProductDao;
import cn.yjw.product.service.ProductService;
import cn.yjw.utils.page.Page;

/**
 * @author yangjunwei
 */
public class ProductServiceImpl implements ProductService {

	private SessionFactory sessionFactory;
	
	private ProductDao productDao;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public ProductDao getProductDao() {
		return productDao;
	}
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	/**
	 * 新增商品信息
	 */
	public void addProduct(Product product) {
		
		// 设置商品对象的创建时间和修改时间
		Date curDate = new Date();
		product.setCreateTime(curDate);
		product.setModifyTime(curDate);
		
		// 保存商品对象
		productDao.saveProduct(product);
		
		// 保存商品图片集合
		for(int i=0; i<product.getPimgs().size(); i++) {
			ProductImage pimg = product.getPimgs().get(i);
			if(pimg != null) {
				// 设置商品图片与商品的关联关系
				pimg.setProduct(product);
				// 保存商品图片
				productDao.saveProductImage(pimg);
			}
		}
		
		// 保存子商品集合
		for(int i=0; i<product.getPsubs().size(); i++) {
			ProductSub psub = product.getPsubs().get(i);
			if(psub != null) {
				// 设置自商品与商品的关联关系
				psub.setProduct(product);
				// 保存自商品
				productDao.saveProductSub(psub);
			}
		}
	}
	
	/**
	 * 更新商品信息
	 */
	public void updateProduct(Product product) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 设置商品对象的创建时间和修改时间
		Date curDate = new Date();
		product.setModifyTime(curDate);
		
		// 获取临时商品对象
		Product productTmp = productDao.getProductById(product.getId());
		// 设置商品创建时间
		product.setCreateTime(productTmp.getCreateTime());
		// 如果客户端没有修改商品展示图片，则使用原来的图片
		if(product.getImage() == null) {
			product.setImage(productTmp.getImage());
		}
		// 清除Session缓存中的productTmp对象
		sess.evict(productTmp);
		
		// 保存商品对象
		productDao.saveProduct(product);
		
		// 删除不在pimgs集合中的商品图片对象
		List<Long> pimgIds = new ArrayList<Long>();
		for(int i=0; i<product.getPimgs().size(); i++) {
			ProductImage pimg = product.getPimgs().get(i);
			if(pimg != null && pimg.getId() != null) {
				pimgIds.add(pimg.getId());
			}
		}
		productDao.deleteProductImagesByCondition(pimgIds, product.getId());
		
		// 保存pimgs集合中的商品图片对象
		for(int i=0; i<product.getPimgs().size(); i++) {
			ProductImage pimg = product.getPimgs().get(i);
			if(pimg != null) {
				if(pimg.getId() != null && pimg.getImage() == null) {
					// 获取临时商品图片对象
					ProductImage pimgTmp = productDao.getProductImageById(pimg.getId());
					// 如果客户端没有修改商品图片，则使用原来的图片
					pimg.setImage(pimgTmp.getImage());
					// 清除Session缓存中的pimgTmp对象
					sess.evict(pimgTmp);
				}
				// 设置商品图片与商品的关联关系
				pimg.setProduct(product);
				// 保存商品图片
				productDao.saveProductImage(pimg);
			}
		}
		
		// 删除不在psubs集合中的子商品对象
		List<Long> psubIds = new ArrayList<Long>();
		for(int i=0; i<product.getPsubs().size(); i++) {
			ProductSub psub = product.getPsubs().get(i);
			if(psub != null && psub.getId() != null) {
				psubIds.add(psub.getId());
			}
		}
		productDao.deleteProductSubsByCondition(psubIds, product.getId());
		
		// 保存psubs集合中的子商品对象
		for(int i=0; i<product.getPsubs().size(); i++) {
			ProductSub psub = product.getPsubs().get(i);
			if(psub != null) {
				// 设置自商品与商品的关联关系
				psub.setProduct(product);
				// 保存自商品
				productDao.saveProductSub(psub);
			}
		}
		
	}
	
	/**
	 * 根据ID获取商品信息
	 */
	public Product getProductById(Long id) {
		
		Product product = productDao.getProductById(id);
		
		if (product != null) {
			
			// 初始化关联商品分类
			Hibernate.initialize(product.getPc());
			
			// 初始化商品分类对应的参数
			Hibernate.initialize(product.getPc().getParams());
			// 初始化商品参数关联的所有参数项
			for(int i=0; i<product.getPc().getParams().size(); i++) {
				Parameter param = product.getPc().getParams().get(i);
				Hibernate.initialize(param.getPitems());
			}
			
			// 初始化商品分类对应的属性
			Hibernate.initialize(product.getPc().getAttrs());
			// 初始化商品属性关联的所有属性值
			for(int i=0; i<product.getPc().getAttrs().size(); i++) {
				Attribute attr = product.getPc().getAttrs().get(i);
				Hibernate.initialize(attr.getAtopts());
			}
			
			// 初始化关联商品图片
			Hibernate.initialize(product.getPimgs());
			
			// 初始化关联商品规格
			Hibernate.initialize(product.getSps());
			// 初始化商品规格关联的规格值
			for(int i=0; i<product.getSps().size(); i++) {
				Specification sp = product.getSps().get(i);
				Hibernate.initialize(sp.getSpvs());
			}
			
			// 初始化关联子商品
			Hibernate.initialize(product.getPsubs());
			//初始化子商品关联的规格值
			for(int i=0; i<product.getPsubs().size(); i++) {
				ProductSub psub = product.getPsubs().get(i);
				Hibernate.initialize(psub.getSpvs());
			}
			
			// 初始化关联属性值
			Hibernate.initialize(product.getAtopts());
			
			// 初始化关联参数值
			Hibernate.initialize(product.getPpitems());
			
			// 设置商品参数项的当前值（curPitemVal）
			for(int i=0; i<product.getPc().getParams().size(); i++) {
				Parameter param = product.getPc().getParams().get(i);
				for(int j=0; j<param.getPitems().size(); j++) {
					ParameterItem pitem = param.getPitems().get(j);
					for(int k=0; k<product.getPpitems().size(); k++) {
						ProductParamItemValue ppitem = product.getPpitems().get(k);
						if(ppitem.getPitem().getId().equals(pitem.getId())) {
							pitem.setCurPitemVal(ppitem.getValue());
							break;
						}
					}
				}
			}
			
			// 设置商品属性的当前值（curAtoptId）
			for(int i=0; i<product.getPc().getAttrs().size(); i++) {
				Attribute attr = product.getPc().getAttrs().get(i);
				for(int j=0; j<product.getAtopts().size(); j++) {
					AttributeOption atopt = product.getAtopts().get(j);
					if(atopt.getAttr().getId().equals(attr.getId())) {
						attr.setCurAtoptId(atopt.getId());
						break;
					}
				}
			}
		}
		
		return product;
	}
	
	/**
	 * 根据指定条件分页查询商品信息
	 */
	public Page<Product> getProductsByCondition(Map<String, Object> condition) {
		
		Integer pageSize = (Integer) condition.get("pageSize");
		Integer pageNumber = (Integer) condition.get("pageNumber");
		
		// 初始化商品分页对象
		Page<Product> productPage = new Page<Product>(pageSize, pageNumber);
		
		// 设置分页对象的记录总数
		int totalCount = productDao.getProductCountByCondition(condition);
		productPage.setTotalCount(totalCount);
		
		// 设置分页对象的请求页码
		if(productPage.getPageNumber() < 1) {
			productPage.setPageNumber(1);
		} else if(productPage.getPageNumber() > productPage.getTotalPage()) {
			productPage.setPageNumber(productPage.getTotalPage());
		}
		
		// 重新设置查询条件
		condition.remove("pageNumber");
		condition.put("startIndex", productPage.getStartIndex());
		
		// 根据指定条件分页查询商品信息
		List<Product> products = productDao.getProductsByCondition(condition);
		// 加载商品分类信息
		for(int i=0; i<products.size(); i++) {
			Product product = products.get(i);
			Hibernate.initialize(product.getPc());
		}
		productPage.setList(products);
		
		return productPage;
	}
	
	/**
	 * 根据ID集合批量删除商品
	 */
	public void deleteProductsByIds(Long[] ids) {
		
		// 查询待删除的商品对象
		List<Product> products = productDao.getProductsByIds(ids);
		// 批量删除商品
		for (Product product : products) {
			deleteProduct(product);
		}
	}
	
	/**
	 * 删除商品
	 */
	public void deleteProduct(Product product) {
		
		// 删除关联的商品图片
		productDao.deleteProductImagesByProductId(product.getId());
		
		// 删除关联的子商品
		productDao.deleteProductSubsByProductId(product.getId());
		
		// 删除商品
		productDao.deleteProduct(product);
	}

}
