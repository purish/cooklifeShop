package cn.yjw.content.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.content.bean.Navigation;
import cn.yjw.content.dao.NavigationDao;

/**
 * @author yangjunwei
 */
public class NavigationDaoImpl implements NavigationDao {
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 保存导航信息
	 */
	public void saveNavigation(Navigation nav) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(nav);
	}
	
	/**
	 * 根据ID获取导航对象
	 */
	public Navigation getNavigationById(Long id) {
		
		Session sess = sessionFactory.getCurrentSession();
		Navigation nav = (Navigation) sess.get(Navigation.class, id);
		
		return nav;
	}
	
	/**
	 * 获取全部导航对象
	 */
	public List<Navigation> getAllNavigations() {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT nav FROM Navigation nav ");
		hql.append("ORDER BY nav.order ");
		
		// 执行查询
		Query query = sess.createQuery(hql.toString());
		List<Navigation> navs = query.list();
		
		return navs;
	}

	/**
	 * 根据ID集合批量删除导航信息
	 */
	public void deleteNavigationsByIds(Long[] ids) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE FROM Navigation nav ");
		hql.append("WHERE nav.id IN (:ids) ");
		
		// 设置参数，执行删除
		Query query = sess.createQuery(hql.toString());
		query.setParameterList("ids", ids);
		query.executeUpdate();
	}

}
