package cn.yjw.product.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.product.bean.Specification;
import cn.yjw.product.bean.SpecificationValue;
import cn.yjw.product.dao.SpecificationDao;

/**
 * @author yangjunwei
 */
public class SpecificationDaoImpl implements SpecificationDao {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * 保存商品规格信息
	 */
	public void saveSpecification(Specification sp) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(sp);
	}
	
	/**
	 * 保存商品规格值信息
	 */
	public void saveSpecificationValue(SpecificationValue spv) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(spv);
	}
	
	/**
	 * 根据ID获取商品规格对象
	 */
	public Specification getSpecificationById(Long id) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		Specification sp = (Specification) sess.get(Specification.class, id);
		
		return sp;
	}

	/**
	 * 根据ID获取商品规格值对象
	 */
	public SpecificationValue getSpecificationValueById(Long id) {

		Session sess = sessionFactory.getCurrentSession();
		SpecificationValue spv = (SpecificationValue) sess.get(SpecificationValue.class, id);
		
		return spv;
	}
	
	/**
	 * 根据指定条件获取规格值信息
	 */
	public List<SpecificationValue> getSpecificationValuesByCondition(
			List<Long> spvIds, Long spId) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT spv FROM SpecificationValue spv ");
		hql.append("WHERE spv.id NOT IN (:spvIds) ");
		hql.append("and spv.sp.id=:spId ");
		
		// 设置参数，执行查询
		Query query = sess.createQuery(hql.toString());
		query.setParameterList("spvIds", spvIds);
		query.setLong("spId", spId);
		List<SpecificationValue> spvs = query.list();
		
		return spvs;
	}
	
	/**
	 * 根据ID集合，批量删除规格值
	 */
	public void deleteSpecificationValuesByCondition(List<Long> spvIds, Long spId) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造批量删除语句
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE FROM SpecificationValue spv ");
		hql.append("WHERE spv.id NOT IN (:spvIds) ");
		hql.append("and spv.sp.id=:spId ");
		
		// 设置参数，执行批量删除
		Query query = sess.createQuery(hql.toString());
		query.setParameterList("spvIds", spvIds);
		query.setLong("spId", spId);
		
		query.executeUpdate();
	}
	
	/**
	 * 获取符合条件的规格总数
	 */
	public int getSpecificationCountByCondition(Map<String, Object> condition) {
		
		// 获取查询的属性和属性值
		String searchProperty = (String) condition.get("searchProperty");
		String searchValue = (String) condition.get("searchValue");
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT COUNT(*) FROM Specification sp ");
		hql.append("WHERE 1=1 ");
		if(StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)){
			if(searchProperty.equals("all")){
				hql.append("AND (sp.name LIKE '%").append(searchValue).append("%' ");
				hql.append("OR sp.memo LIKE '%").append(searchValue).append("%') ");
			} else {
				hql.append("AND sp.").append(searchProperty).append(" LIKE '%")
						.append(searchValue).append("%' ");
			}
		}
		
		// 执行查询
		Query query = sess.createQuery(hql.toString());
		int count = ((Long)query.uniqueResult()).intValue();
		
		return count;
	}

	/**
	 * 根据指定条件分页查询规格信息
	 */
	public List<Specification> getSpecificationsByCondition(Map<String, Object> condition) {
		
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
		hql.append("SELECT sp FROM Specification sp ");
		hql.append("WHERE 1=1 ");
		if(StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)){
			if(searchProperty.equals("all")){
				hql.append("AND (sp.name LIKE '%").append(searchValue).append("%' ");
				hql.append("OR sp.memo LIKE '%").append(searchValue).append("%') ");
			} else {
				hql.append("AND sp.").append(searchProperty).append(" LIKE '%")
						.append(searchValue).append("%' ");
			}
		}
		hql.append("ORDER BY sp.").append(orderProperty).append(" ").append(orderDirection);
		
		// 执行分页查询
		Query query = sess.createQuery(hql.toString());
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		List<Specification> spList = query.list();
		
		return spList;
	}
	
	/**
	 * 根据ID集合获取规格对象
	 */
	public List<Specification> getSpecificationsByIds(Long[] ids) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT sp FROM Specification sp ");
		hql.append("WHERE sp.id in (:ids) ");
		
		// 设置参数，执行查询
		Query query = sess.createQuery(hql.toString());
		query.setParameterList("ids", ids);
		List<Specification> sps = query.list();
		
		return sps;
	}
	
	/**
	 * 根据规格ID删除所有关联的规格值
	 */
	public void deleteSpecificationValuesBySpId(Long spId) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造批量删除HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE FROM SpecificationValue spv ");
		hql.append("WHERE spv.sp.id=:spId ");
		
		// 设置条件参数，执行删除
		Query query = sess.createQuery(hql.toString());
		query.setLong("spId", spId);
		query.executeUpdate();
		
	}
	
	/**
	 * 删除规格对象
	 */
	public void deleteSpecification(Specification sp) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.delete(sp);
	}
	
	/**
	 * 获取所有规格信息
	 */
	public List<Specification> getAllSpecifications() {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT sp FROM Specification sp ");
		hql.append("ORDER BY sp.order ");
		
		// 执行查询
		Query query = sess.createQuery(hql.toString());
		List<Specification> sps = query.list();
		
		return sps;
	}
	
}
