package cn.yjw.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.product.bean.Product;
import cn.yjw.product.bean.ProductSub;
import cn.yjw.product.bean.Specification;
import cn.yjw.product.bean.SpecificationValue;
import cn.yjw.product.dao.ProductDao;
import cn.yjw.product.dao.SpecificationDao;
import cn.yjw.product.service.SpecificationService;
import cn.yjw.utils.page.Page;

/**
 * @author yangjunwei
 */
public class SpecificationServiceImpl implements SpecificationService {
	
	private SessionFactory sessionFactory;

	private SpecificationDao spDao;
	
	private ProductDao productDao;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SpecificationDao getSpDao() {
		return spDao;
	}
	public void setSpDao(SpecificationDao spDao) {
		this.spDao = spDao;
	}
	
	public ProductDao getProductDao() {
		return productDao;
	}
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	/**
	 * 新增商品规格信息
	 */
	public void addSpecification(Specification sp) {
		
		// 设置规格对象的创建时间和修改时间
		Date curDate = new Date();
		sp.setCreateTime(curDate);
		sp.setModifyTime(curDate);
		
		// 保存规格对象
		spDao.saveSpecification(sp);
		
		// 保存规格值对象
		for(int i=0; i < sp.getSpvs().size(); i++) {
			SpecificationValue spv = sp.getSpvs().get(i);
			if(spv != null) {
				// 设置规格值对象的创建时间和修改时间
				spv.setCreateTime(curDate);
				spv.setModifyTime(curDate);
				// 保存规格值对象
				spDao.saveSpecificationValue(spv);
			}
		}
	}

	/**
	 * 修改商品规格信息
	 */
	public void updateSpecification(Specification sp) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 设置规格对象的修改时间和创建时间
		Date curDate = new Date();
		sp.setModifyTime(curDate);
		// 获取临时规格对象
		Specification spTmp = spDao.getSpecificationById(sp.getId());
		sp.setCreateTime(spTmp.getCreateTime());
		sess.evict(spTmp);// 清除Session缓存中的spTmp对象
		
		// 保存规格对象
		spDao.saveSpecification(sp);
		
		// 删除不在spvs集合中的规格值对象
		List<Long> spvIds = new ArrayList<Long>();
		for (int i = 0; i < sp.getSpvs().size(); i++) {
			SpecificationValue spv = sp.getSpvs().get(i);
			if (spv != null && spv.getId() != null) {
				spvIds.add(spv.getId());
			}
		}
		// 获取不在spvs集合中的规格值对象
		List<SpecificationValue> spvs = spDao.getSpecificationValuesByCondition(spvIds, sp.getId());
		// 解除规格值与子商品的关联关系
		for(int i=0; i<spvs.size(); i++) {
			SpecificationValue spv = spvs.get(i);
			List<ProductSub> psubs = spv.getPsubs();
			for(int j=0; j<psubs.size(); j++) {
				psubs.get(j).getSpvs().remove(spv);
				productDao.saveProductSub(psubs.get(j));
			}
			sess.flush();
		}
		// 批量删除规格值
		spDao.deleteSpecificationValuesByCondition(spvIds, sp.getId());
		
		// 保存spvs集合中的规格值对象
		for (int i = 0; i < sp.getSpvs().size(); i++) {
			SpecificationValue spv = sp.getSpvs().get(i);
			if(spv != null){
				// 设置规格值的修改时间
				spv.setModifyTime(curDate);
				
				if(spv.getId() != null){
					// 获取临时规格值对象
					SpecificationValue spvTmp = spDao.getSpecificationValueById(spv.getId());
					// 设置规格值的创建时间
					spv.setCreateTime(spvTmp.getCreateTime());
					// 如果客户端没有修改参数值图片，则使用原来的
					if(spv.getImage() == null){
						spv.setImage(spvTmp.getImage());
					}
					// 清除Session缓存中的spvTmp对象
					sess.evict(spvTmp);
					
				} else{
					spv.setCreateTime(curDate);
				}
				
				spDao.saveSpecificationValue(spv);
			}
		}
	}
	
	/**
	 * 根据ID查询商品规格信息
	 */
	public Specification getSpecificationById(Long id) {
		
		// 获取商品规格对象
		Specification sp = spDao.getSpecificationById(id);
		
		// 初始化规格值结合的代理对象
		if(sp != null){
			Hibernate.initialize(sp.getSpvs());
		}
		
		return sp;
	}
	
	/**
	 * 根据指定条件分页查询规格信息
	 */
	public Page<Specification> getSpecificationsByCondition(Map<String, Object> condition) {
		
		Integer pageSize = (Integer) condition.get("pageSize");
		Integer pageNumber = (Integer) condition.get("pageNumber");
		
		// 初始化规格分页对象
		Page<Specification> spPage = new Page<Specification>(pageSize, pageNumber);
		
		// 设置分页对象的总记录数
		int totalCount = spDao.getSpecificationCountByCondition(condition);
		spPage.setTotalCount(totalCount);
		
		// 设置分页对象的请求页码
		if(spPage.getPageNumber() < 1){
			spPage.setPageNumber(1);
		} else if(spPage.getPageNumber() > spPage.getTotalPage()){
			spPage.setPageNumber(spPage.getTotalPage());
		}
		
		// 重新设置查询条件
		condition.remove("pageNumber");
		condition.put("startIndex", spPage.getStartIndex());
		
		// 根据指定条件分页查询规格信息
		List<Specification> spList = spDao.getSpecificationsByCondition(condition);
		// 初始化规格值代理对象
		for(int i=0; i<spList.size(); i++){
			Hibernate.initialize(spList.get(i).getSpvs());
		}
		spPage.setList(spList);
		
		return spPage;
	}
	
	/**
	 * 根据ID集合批量删除规格信息
	 */
	public void deleteSpecificationsByIds(Long[] ids) {
		
		// 查询待删除的规格对象
		List<Specification> sps = spDao.getSpecificationsByIds(ids);
		
		// 批量删除商品规格
		for(Specification sp : sps) {
			deleteSpecification(sp);
		}
		
	}
	
	/**
	 * 删除商品规格
	 */
	public void deleteSpecification(Specification sp) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 解除规格值与子商品的关联关系
		List<SpecificationValue> spvs = sp.getSpvs();
		for(SpecificationValue spv : spvs) {
			List<ProductSub> psubs = spv.getPsubs();
			for(ProductSub psub : psubs) {
				psub.getSpvs().remove(spv);
				productDao.saveProductSub(psub);
			}
			sess.flush();
		}
		
		// 解除规格与商品的关联关系
		List<Product> products = sp.getProducts();
		for(Product product : products) {
			product.getSps().remove(sp);
			productDao.saveProduct(product);
		}
		sess.flush();
		
		// 批量删除规格值
		spDao.deleteSpecificationValuesBySpId(sp.getId());
		
		// 删除商品规格
		spDao.deleteSpecification(sp);
		
	}
	
	/**
	 * 获取所有规格信息
	 */
	public List<Specification> getAllSpecifications() {
		
		List<Specification> sps = spDao.getAllSpecifications();
		
		// 初始化规格值代理对象
		for(int i=0; i<sps.size(); i++) {
			Hibernate.initialize(sps.get(i).getSpvs());
		}
		
		return sps;
	}
	
}
