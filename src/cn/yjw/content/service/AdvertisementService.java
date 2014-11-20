package cn.yjw.content.service;

import java.util.Map;

import cn.yjw.content.bean.Advertisement;
import cn.yjw.utils.page.Page;

/**
 * @author yangjunwei
 */
public interface AdvertisementService {
	
	/**
	 * 保存广告信息
	 * @param ad
	 */
	public void saveAdvertisement(Advertisement ad);
	
	/**
	 * 根据ID获取广告对象
	 * @param id
	 * @return
	 */
	public Advertisement getAdvertisementById(Long id);
	
	/**
	 * 按指定条件分页查询广告信息
	 * @param condition
	 * @return
	 */
	public Page<Advertisement> getAdvertisementsByCondition(Map<String, Object> condition);
	
	/**
	 * 根据ID集合批量删除广告
	 * @param ids
	 */
	public void deleteAdvertisementsByIds(Long[] ids);
	
}
