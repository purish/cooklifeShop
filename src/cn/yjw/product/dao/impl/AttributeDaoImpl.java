package cn.yjw.product.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.product.bean.Attribute;
import cn.yjw.product.bean.AttributeOption;
import cn.yjw.product.dao.AttributeDao;

/**
 * @author yangjunwei
 */
public class AttributeDaoImpl implements AttributeDao {
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 保存商品属性
	 */
	public void saveAttribute(Attribute attr) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(attr);
	}
	
	/**
	 * 保存商品属性值
	 */
	public void saveAttributeOption(AttributeOption atopt) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(atopt);
	}

	/**
	 * 根据ID查询商品属性
	 */
	public Attribute getAttributeById(Long id) {
		
		Session sess = sessionFactory.getCurrentSession();
		Attribute attr = (Attribute) sess.get(Attribute.class, id);
		
		return attr;
	}

	/**
	 * 根据ID查询商品属性值
	 */
	public AttributeOption getAttributeOptionById(Long id) {
		
		Session sess = sessionFactory.getCurrentSession();
		AttributeOption atopt = (AttributeOption) sess.get(AttributeOption.class, id);
		
		return atopt;
	}
	
	/**
	 * 根据指定条件获取属性值集合
	 */
	public List<AttributeOption> getAttributeOptionsByCondition(
			List<Long> atoptIds, Long attrId) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT atopt FROM AttributeOption atopt ");
		hql.append("WHERE atopt.id NOT IN (:atoptIds) ");
		hql.append("and atopt.attr.id=:attrId ");
		
		// 设置条件，执行查询
		Query query = sess.createQuery(hql.toString());
		query.setParameterList("atoptIds", atoptIds);
		query.setLong("attrId", attrId);
		List<AttributeOption> atopts = query.list();
		
		return atopts;
	}
	
	/**
	 * 根据指定条件批量删除属性值
	 */
	public void deleteAttributeOptionsByCondition(List<Long> atoptIds, Long attrId) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造批量删除语句
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE FROM AttributeOption atopt ");
		hql.append("WHERE atopt.id NOT IN (:atoptIds) ");
		hql.append("and atopt.attr.id=:attrId ");
		
		// 设置条件，执行删除
		Query query = sess.createQuery(hql.toString());
		query.setParameterList("atoptIds", atoptIds);
		query.setLong("attrId", attrId);
		
		query.executeUpdate();
	}
	
	/**
	 * 获取符合条件的属性总数
	 */
	public int getAttributeCountByCondition(Map<String, Object> condition) {
		
		// 获取查询的属性和属性值
		String searchProperty = (String) condition.get("searchProperty");
		String searchValue = (String) condition.get("searchValue");
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT COUNT(*) FROM Attribute attr ");
		hql.append("WHERE 1=1 ");
		if(StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)){
			if(searchProperty.equals("all")){
				hql.append("AND attr.name LIKE '%").append(searchValue).append("%' ");
			} else {
				hql.append("AND attr.").append(searchProperty).append(" LIKE '%")
						.append(searchValue).append("%' ");
			}
		}
		
		// 执行查询
		Query query = sess.createQuery(hql.toString());
		int count = ((Long)query.uniqueResult()).intValue();
		
		return count;
	}

	/**
	 * 根据指定条件分页查询商品属性
	 */
	public List<Attribute> getAttributesByCondition(Map<String, Object> condition) {
		
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
		hql.append("SELECT attr FROM Attribute attr ");
		hql.append("WHERE 1=1 ");
		if(StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)){
			if(searchProperty.equals("all")){
				hql.append("AND attr.name LIKE '%").append(searchValue).append("%' ");
			} else {
				hql.append("AND attr.").append(searchProperty).append(" LIKE '%")
						.append(searchValue).append("%' ");
			}
		}
		hql.append("ORDER BY attr.").append(orderProperty).append(" ").append(orderDirection);
		
		// 执行分页查询
		Query query = sess.createQuery(hql.toString());
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		List<Attribute> attrList = query.list();
		
		return attrList;
	}
	
	/**
	 * 根据商品分类ID查询关联的属性信息
	 */
	public List<Attribute> getAttributesByPcId(Long pcId) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造条件查询HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT attr FROM Attribute attr ");
		hql.append("WHERE attr.pc.id=:pcId ");
		hql.append("ORDER BY attr.order ");
		
		// 设置参数，执行查询
		Query query = sess.createQuery(hql.toString());
		query.setLong("pcId", pcId);
		List<Attribute> attrs = query.list();
		
		return attrs;
	}
	
	/**
	 * 根据ID集合获取商品属性
	 */
	public List<Attribute> getAttributesByIds(Long[] ids) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT attr FROM Attribute attr ");
		hql.append("WHERE attr.id IN (:ids) ");
		
		// 设置参数，执行查询
		Query query = sess.createQuery(hql.toString());
		query.setParameterList("ids", ids);
		List<Attribute> attrs = query.list();
		
		return attrs;
	}

	/**
	 * 根据属性ID批量删除关联的属性值
	 */
	public void deleteAttributeOptionsByAttrId(Long attrId) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造批量删除HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE FROM AttributeOption atopt ");
		hql.append("WHERE atopt.attr.id=:attrId ");
		
		// 设置参数，执行删除
		Query query = sess.createQuery(hql.toString());
		query.setLong("attrId", attrId);
		query.executeUpdate();
		
	}
	
	/**
	 * 删除商品属性
	 */
	public void deleteAttribute(Attribute attr) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.delete(attr);
	}
	
}
