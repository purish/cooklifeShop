package cn.yjw.content.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.content.bean.Navigation;
import cn.yjw.content.dao.NavigationDao;
import cn.yjw.content.service.NavigationService;

/**
 * @author yangjunwei
 */
public class NavigationServiceImpl implements NavigationService {
	
	private SessionFactory sessionFactory;
	
	private NavigationDao navDao;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public NavigationDao getNavDao() {
		return navDao;
	}
	public void setNavDao(NavigationDao navDao) {
		this.navDao = navDao;
	}

	/**
	 * 保存导航信息
	 */
	public void saveNavigation(Navigation nav) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 设置导航对象的修改时间
		Date curDate = new Date();
		nav.setModifyTime(curDate);
		
		// 设置创建时间
		if(nav.getId() != null) {
			Navigation navTmp = navDao.getNavigationById(nav.getId());
			nav.setCreateTime(navTmp.getCreateTime());
			sess.evict(navTmp);
		} else {
			nav.setCreateTime(curDate);
		}
		
		navDao.saveNavigation(nav);
	}
	
	/**
	 * 根据ID获取导航对象
	 */
	public Navigation getNavigationById(Long id) {
		
		Navigation nav = navDao.getNavigationById(id);
		
		return nav;
	}
	
	/**
	 * 获取全部导航对象
	 */
	public List<Navigation> getAllNavigations() {
		
		List<Navigation> navs = navDao.getAllNavigations();
		
		return navs;
	}

	/**
	 * 根据ID集合批量删除导航信息
	 */
	public void deleteNavigationsByIds(Long[] ids) {
		
		navDao.deleteNavigationsByIds(ids);
	}

}
