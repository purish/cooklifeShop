package cn.yjw.product.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.product.bean.ProductCategory;
import cn.yjw.product.dao.ProductCategoryDao;


/**
 * @author yangjunwei
 */
public class ProductCategoryDaoImpl implements ProductCategoryDao {
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 保存商品分类信息
	 */
	public void saveProductCategory(ProductCategory pc) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(pc);
	}
	
	/**
	 * 根据ID获取商品分类对象
	 */
	public ProductCategory getProductCategoryById(Long id) {
		
		Session sess = sessionFactory.getCurrentSession();
		ProductCategory pc = (ProductCategory) sess.get(ProductCategory.class, id);
		
		return pc;
	}
	
	/**
	 * 获取所有顶级商品分类对象
	 */
	public List<ProductCategory> getAllProductCategorys() {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT pc FROM ProductCategory pc ");
		hql.append("WHERE pc.parent.id IS NULL ");
		hql.append("ORDER BY pc.order ");
		
		// 执行查询
		Query query = sess.createQuery(hql.toString());
		List<ProductCategory> pcs = query.list();
		
		return pcs;
	}

	/**
	 * 删除商品分类
	 */
	public void deleteProductCategory(ProductCategory pc) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.delete(pc);
	}
	
}
