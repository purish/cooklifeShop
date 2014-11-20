package cn.yjw.content.service;

import java.util.List;

import cn.yjw.content.bean.Navigation;

/**
 * @author yangjunwei
 */
public interface NavigationService {
	
	/**
	 * 保存导航信息
	 * @param nav
	 */
	public void saveNavigation(Navigation nav);
	
	/**
	 * 根据ID获取导航对象
	 * @param id
	 * @return
	 */
	public Navigation getNavigationById(Long id);
	
	/**
	 * 获取全部导航对象
	 * @return
	 */
	public List<Navigation> getAllNavigations();
	
	/**
	 * 根据ID集合批量删除导航信息
	 * @param ids
	 */
	public void deleteNavigationsByIds(Long[] ids);

}
