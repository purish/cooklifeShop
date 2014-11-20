package cn.yjw.content.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.content.bean.Advertisement;
import cn.yjw.content.dao.AdvertisementDao;
import cn.yjw.content.service.AdvertisementService;
import cn.yjw.utils.page.Page;

/**
 * @author yangjunwei
 */
public class AdvertisementServiceImpl implements AdvertisementService {
	
	private SessionFactory sessionFactory;
	
	private AdvertisementDao adDao;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public AdvertisementDao getAdDao() {
		return adDao;
	}
	public void setAdDao(AdvertisementDao adDao) {
		this.adDao = adDao;
	}

	/**
	 * 保存广告信息
	 */
	public void saveAdvertisement(Advertisement ad) {
		
		// 获取数据库回话对象
		Session sess = sessionFactory.getCurrentSession();
		
		// 设置文章对象的修改时间
		Date curDate = new Date();
		ad.setModifyTime(curDate);
		
		// 设置广告对象的创建时间和图片
		if(ad.getId() != null) {
			Advertisement adTmp = adDao.getAdvertisementById(ad.getId());
			ad.setCreateTime(adTmp.getCreateTime());
			if(ad.getImage() == null) {
				ad.setImage(adTmp.getImage());
			}
			// 清除Session缓存中的adTmp对象
			sess.evict(adTmp);
		} else {
			ad.setCreateTime(curDate);
		}
		
		adDao.saveAdvertisement(ad);
	}
	
	/**
	 * 根据ID获取广告对象
	 */
	public Advertisement getAdvertisementById(Long id) {
		
		Advertisement ad = adDao.getAdvertisementById(id);
		
		return ad;
	}
	
	/**
	 * 按指定条件分页查询广告信息
	 */
	public Page<Advertisement> getAdvertisementsByCondition(Map<String, Object> condition) {
		
		Integer pageSize = (Integer) condition.get("pageSize");
		Integer pageNumber = (Integer) condition.get("pageNumber");
		
		// 初始化广告分页对象
		Page<Advertisement> adPage = new Page<Advertisement>(pageSize, pageNumber);
		
		// 设置分页对象的总记录数
		int totalCount = adDao.getAdvertisementCountByCondition(condition);
		adPage.setTotalCount(totalCount);
		
		// 设置分页对象的请求页码
		if(adPage.getPageNumber() < 1){
			adPage.setPageNumber(1);
		} else if(adPage.getPageNumber() > adPage.getTotalPage()){
			adPage.setPageNumber(adPage.getTotalPage());
		}
		
		// 重新设置查询条件
		condition.remove("pageNumber");
		condition.put("startIndex", adPage.getStartIndex());
		
		// 根据指定条件分页查询广告信息
		List<Advertisement> ads = adDao.getAdvertisementsByCondition(condition);
		adPage.setList(ads);
		
		return adPage;
	}
	
	/**
	 * 根据ID集合批量删除广告
	 */
	public void deleteAdvertisementsByIds(Long[] ids) {
		
		adDao.deleteAdvertisementsByIds(ids);
	}

}
