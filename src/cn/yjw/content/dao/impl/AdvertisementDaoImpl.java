package cn.yjw.content.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.content.bean.Advertisement;
import cn.yjw.content.dao.AdvertisementDao;

/**
 * @author yangjunwei
 */
public class AdvertisementDaoImpl implements AdvertisementDao {
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 保存广告信息
	 */
	public void saveAdvertisement(Advertisement ad) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(ad);
	}
	
	/**
	 * 根据ID获取广告对象
	 */
	public Advertisement getAdvertisementById(Long id) {
		
		Session sess = sessionFactory.getCurrentSession();
		Advertisement ad = (Advertisement) sess.get(Advertisement.class, id);
		
		return ad;
	}
	
	/**
	 * 获取符合条件的广告总数
	 */
	public int getAdvertisementCountByCondition(Map<String, Object> condition) {
		
		// 获取查询的属性和属性值
		String searchProperty = (String) condition.get("searchProperty");
		String searchValue = (String) condition.get("searchValue");
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT COUNT(*) FROM Advertisement ad ");
		hql.append("WHERE 1=1 ");
		if(StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)) {
			if(searchProperty.equals("all")){
				hql.append("AND ad.title LIKE '%").append(searchValue).append("%' ");
			} else {
				hql.append("AND ad.").append(searchProperty).append(" LIKE '%")
						.append(searchValue).append("%' ");
			}
		}
		
		// 执行查询
		Query query = sess.createQuery(hql.toString());
		int count = ((Long) query.uniqueResult()).intValue();
		
		return count;
	}

	/**
	 * 按指定条件分页查询广告信息
	 */
	public List<Advertisement> getAdvertisementsByCondition(Map<String, Object> condition) {
		
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
		hql.append("SELECT ad FROM Advertisement ad ");
		hql.append("WHERE 1=1 ");
		if(StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)) {
			if(searchProperty.equals("all")){
				hql.append("AND ad.title LIKE '%").append(searchValue).append("%' ");
			} else {
				hql.append("AND ad.").append(searchProperty).append(" LIKE '%")
						.append(searchValue).append("%' ");
			}
		}
		hql.append("ORDER BY ad.").append(orderProperty).append(" ").append(orderDirection);
		
		// 执行分页查询
		Query query = sess.createQuery(hql.toString());
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		List<Advertisement> ads = query.list();
		
		return ads;
	}
	
	/**
	 * 根据ID集合批量删除广告
	 */
	public void deleteAdvertisementsByIds(Long[] ids) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE FROM Advertisement ad ");
		hql.append("WHERE ad.id IN (:ids) ");
		
		// 设置参数，执行删除
		Query query = sess.createQuery(hql.toString());
		query.setParameterList("ids", ids);
		query.executeUpdate();
	}

}
