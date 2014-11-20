package cn.yjw.product.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.product.bean.Brand;
import cn.yjw.product.dao.BrandDao;

/**
 * @author yangjunwei
 */
public class BrandDaoImpl implements BrandDao {
	
	private SessionFactory sessionFactory;
	
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
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(brand);
	}
	
	/**
	 * 按指定条件分页查询品牌信息
	 */
	public List<Brand> getBrandsByCondition(Map<String, Object> condition) {
		
		// 获取查询条件
		Integer startIndex = (Integer) condition.get("startIndex");
		Integer pageSize = (Integer) condition.get("pageSize");
		String searchProperty = (String) condition.get("searchProperty");
		String searchValue = (String) condition.get("searchValue");
		String orderProperty = (String) condition.get("orderProperty");
		String orderDirection = (String) condition.get("orderDirection");
		
		// 获取连接对象
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT b FROM Brand b ");
		hql.append("WHERE 1=1 ");
		if(StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)){
			if(searchProperty.equals("all")){
				hql.append("AND (b.name LIKE '%").append(searchValue).append("%' ");
				hql.append("OR b.url LIKE '%").append(searchValue).append("%') ");
			} else {
				hql.append("AND b.").append(searchProperty).append(" LIKE '%")
						.append(searchValue).append("%' ");
			}
		}
		hql.append("ORDER BY b.").append(orderProperty).append(" ").append(orderDirection);
		
		// 执行分页查询
		Query query = sess.createQuery(hql.toString());
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		List<Brand> brands = query.list();
		
		return brands;
	}
	
	/**
	 * 获取符合条件的品牌总数
	 */
	public int getBrandCountByCondition(Map<String, Object> condition) {
		
		// 获取查询的属性和属性值
		String searchProperty = (String) condition.get("searchProperty");
		String searchValue = (String) condition.get("searchValue");
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT COUNT(*) FROM Brand b ");
		hql.append("WHERE 1=1 ");
		if(StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)){
			if(searchProperty.equals("all")){
				hql.append("AND (b.name LIKE '%").append(searchValue).append("%' ");
				hql.append("OR b.url LIKE '%").append(searchValue).append("%') ");
			} else {
				hql.append("AND b.").append(searchProperty).append(" LIKE '%")
						.append(searchValue).append("%' ");
			}
		}
		
		// 执行查询
		Query query = sess.createQuery(hql.toString());
		int count = ((Long) query.uniqueResult()).intValue();
		
		return count;
	}
	
	/**
	 * 根据ID查询品牌信息
	 */
	public Brand getBrandById(Long id) {
		
		Session sess = sessionFactory.getCurrentSession();
		Brand brand = (Brand) sess.get(Brand.class, id);
		
		return brand;
	}
	
	/**
	 * 获取所有品牌信息
	 */
	public List<Brand> getAllBrands() {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造投影查询的HQL
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT new Brand(id,name) ");
		hql.append("FROM Brand b ");
		hql.append("ORDER BY b.order ");
		
		// 执行查询
		Query query = sess.createQuery(hql.toString());
		List<Brand> brands = query.list();
		
		return brands;
	}
	
	/**
	 * 根据ID集合获取品牌信息集合
	 */
	public List<Brand> getBrandsByIds(Long[] ids) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造投影查询的HQL
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT b FROM Brand b ");
		hql.append("WHERE b.id IN (:ids) ");
		
		// 设置参数，执行查询
		Query query = sess.createQuery(hql.toString());
		query.setParameterList("ids", ids);
		List<Brand> brands = query.list();
		
		return brands;
	}
	
	/**
	 * 删除品牌对象
	 */
	public void deleteBrand(Brand brand) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.delete(brand);
	}

}
