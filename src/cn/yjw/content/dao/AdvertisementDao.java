package cn.yjw.content.dao;

import java.util.List;
import java.util.Map;

import cn.yjw.content.bean.Advertisement;

/**
 * @author yangjunwei
 */
public interface AdvertisementDao {
	
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
	 * 获取符合条件的广告总数
	 * @param condition
	 * @return
	 */
	public int getAdvertisementCountByCondition(Map<String, Object> condition);
	
	/**
	 * 按指定条件分页查询广告信息
	 * @param condition
	 * @return
	 */
	public List<Advertisement> getAdvertisementsByCondition(Map<String, Object> condition);
	
	/**
	 * 根据ID集合批量删除广告
	 * @param ids
	 */
	public void deleteAdvertisementsByIds(Long[] ids);
	
}
