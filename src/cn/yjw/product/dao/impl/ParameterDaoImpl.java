package cn.yjw.product.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.product.bean.Parameter;
import cn.yjw.product.bean.ParameterItem;
import cn.yjw.product.dao.ParameterDao;

/**
 * @author yangjunwei
 */
public class ParameterDaoImpl implements ParameterDao {
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 保存商品参数
	 */
	public void saveParameter(Parameter param) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(param);
	}
	
	/**
	 * 保存参数项信息
	 */
	public void saveParameterItem(ParameterItem pitem) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(pitem);
	}
	
	/**
	 * 根据ID查询商品参数信息
	 */
	public Parameter getParameterById(Long id) {
		
		Session sess = sessionFactory.getCurrentSession();
		Parameter param = (Parameter) sess.get(Parameter.class, id);
		
		return param;
	}
	
	/**
	 * 根据ID查询商品参数项信息
	 */
	public ParameterItem getParameterItemById(Long id) {
		
		Session sess = sessionFactory.getCurrentSession();
		ParameterItem pitem = (ParameterItem) sess.get(ParameterItem.class, id);
		
		return pitem;
	}
	
	/**
	 * 根据指定条件获取参数项集合
	 */
	public List<ParameterItem> getParameterItemsByCondition(
			List<Long> pitemIds, Long paramId) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造批量删除语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT pitem FROM ParameterItem pitem ");
		hql.append("WHERE pitem.id NOT IN (:pitemIds) ");
		hql.append("and pitem.param.id=:paramId ");
		
		// 设置条件，执行删除
		Query query = sess.createQuery(hql.toString());
		query.setParameterList("pitemIds", pitemIds);
		query.setLong("paramId", paramId);
		List<ParameterItem> pitems = query.list();
		
		return pitems;
	}
	
	/**
	 * 根据指定条件批量删除参数项
	 */
	public void deleteParameterItemsByCondition(List<Long> pitemIds, Long paramId) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造批量删除语句
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE FROM ParameterItem pitem ");
		hql.append("WHERE pitem.id NOT IN (:pitemIds) ");
		hql.append("and pitem.param.id=:paramId ");
		
		// 设置条件，执行删除
		Query query = sess.createQuery(hql.toString());
		query.setParameterList("pitemIds", pitemIds);
		query.setLong("paramId", paramId);
		
		query.executeUpdate();
		
	}
	
	/**
	 * 获取符合条件的参数总数
	 */
	public int getParameterCountByCondition(Map<String, Object> condition) {
		
		// 获取查询的属性和属性值
		String searchProperty = (String) condition.get("searchProperty");
		String searchValue = (String) condition.get("searchValue");
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT COUNT(*) FROM Parameter param ");
		hql.append("WHERE 1=1 ");
		if(StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)){
			if(searchProperty.equals("all")){
				hql.append("AND param.name LIKE '%").append(searchValue).append("%' ");
			} else {
				hql.append("AND param.").append(searchProperty).append(" LIKE '%")
						.append(searchValue).append("%' ");
			}
		}
		
		// 执行查询
		Query query = sess.createQuery(hql.toString());
		int count = ((Long)query.uniqueResult()).intValue();
		
		return count;
	}

	/**
	 * 根据指定条件分页查询参数信息
	 */
	public List<Parameter> getParametersByCondition(Map<String, Object> condition) {
		
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
		hql.append("SELECT param FROM Parameter param ");
		hql.append("WHERE 1=1 ");
		if(StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)){
			if(searchProperty.equals("all")){
				hql.append("AND param.name LIKE '%").append(searchValue).append("%' ");
			} else {
				hql.append("AND param.").append(searchProperty).append(" LIKE '%")
						.append(searchValue).append("%' ");
			}
		}
		hql.append("ORDER BY param.").append(orderProperty).append(" ").append(orderDirection);
		
		// 执行分页查询
		Query query = sess.createQuery(hql.toString());
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		List<Parameter> paramList = query.list();
		
		return paramList;
	}
	
	/**
	 * 根据商品分类ID获取参数信息
	 */
	public List<Parameter> getParametersByPcId(Long pcId) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造条件查询HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT param FROM Parameter param ");
		hql.append("WHERE param.pc.id=:pcId ");
		hql.append("ORDER BY param.order ");
		
		// 设置参数，执行查询
		Query query = sess.createQuery(hql.toString());
		query.setLong("pcId", pcId);
		List<Parameter> params = query.list();
		
		return params;
	}
	
	/**
	 * 根据ID集合获取商品参数
	 */
	public List<Parameter> getParametersByIds(Long[] ids) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT param FROM Parameter param ");
		hql.append("WHERE param.id IN (:ids) ");
		
		// 设置参数，执行查询
		Query query = sess.createQuery(hql.toString());
		query.setParameterList("ids", ids);
		List<Parameter> params = query.list();
		
		return params;
	}
	
	/**
	 * 根据参数ID批量删除参数项
	 */
	public void deleteParameterItemsByParamId(Long paramId) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造批量删除HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE FROM ParameterItem pitem ");
		hql.append("WHERE pitem.param.id=:paramId ");
		
		// 设置参数，执行删除
		Query query = sess.createQuery(hql.toString());
		query.setLong("paramId", paramId);
		query.executeUpdate();
		
	}
	
	/**
	 * 删除商品参数
	 */
	public void deleteParameter(Parameter param) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.delete(param);
	}
	
}
