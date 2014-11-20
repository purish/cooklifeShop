package cn.yjw.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.product.bean.Parameter;
import cn.yjw.product.bean.ParameterItem;
import cn.yjw.product.bean.Product;
import cn.yjw.product.bean.ProductCategory;
import cn.yjw.product.bean.ProductParamItemValue;
import cn.yjw.product.dao.ParameterDao;
import cn.yjw.product.dao.ProductDao;
import cn.yjw.product.service.ParameterService;
import cn.yjw.utils.page.Page;

/**
 * @author yangjunwei
 */
public class ParameterServiceImpl implements ParameterService {
	
	private SessionFactory sessionFactory;
	
	private ParameterDao paramDao;
	
	private ProductDao productDao;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public ParameterDao getParamDao() {
		return paramDao;
	}
	public void setParamDao(ParameterDao paramDao) {
		this.paramDao = paramDao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	/**
	 * 添加商品参数
	 */
	public void addParameter(Parameter param) {
		
		// 设置商品参数的修改时间和创建时间
		Date curDate = new Date();
		param.setModifyTime(curDate);
		param.setCreateTime(curDate);
		
		// 保存商品参数对象
		paramDao.saveParameter(param);
		
		// 保存参数项
		for(int i=0; i<param.getPitems().size(); i++) {
			ParameterItem pitem = param.getPitems().get(i);
			if(pitem != null){
				// 设置参数项的创建时间和修改时间
				pitem.setCreateTime(curDate);
				pitem.setModifyTime(curDate);
				// 设置参数项与商品参数的关联关系
				pitem.setParam(param);
				// 保存参数项信息
				paramDao.saveParameterItem(pitem);
			}
		}
	}

	/**
	 * 更新商品参数
	 */
	public void updateParameter(Parameter param) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 设置商品参数的修改时间和创建时间
		Date curDate = new Date();
		param.setModifyTime(curDate);
		// 获取临时参数对象
		Parameter paramTmp = paramDao.getParameterById(param.getId());
		param.setCreateTime(paramTmp.getCreateTime());
		sess.evict(paramTmp);// 清除Session缓存中的paramTmp对象
		
		// 保存商品参数对象
		paramDao.saveParameter(param);
		
		// 删除不在pitems集合中的参数项对象
		List<Long> pitemIds = new ArrayList<Long>();
		for(int i=0; i<param.getPitems().size(); i++) {
			ParameterItem pitem = param.getPitems().get(i);
			if(pitem != null && pitem.getId() != null){
				pitemIds.add(pitem.getId());
			}
		}
		// 获取不在pitems集合内的参数项对象
		List<ParameterItem> pitems = paramDao.getParameterItemsByCondition(pitemIds, param.getId());
		// 解除参数项与商品间的关联关系
		for(int i=0; i<pitems.size(); i++) {
			ParameterItem pitem = pitems.get(i);
			List<ProductParamItemValue> ppitems = pitem.getPpitems();
			for(int j=0; j<ppitems.size(); j++) {
				ProductParamItemValue ppitem = ppitems.get(j);
				Product product = ppitem.getProduct();
				product.getPpitems().remove(ppitem);
				productDao.saveProduct(product);
			}
			sess.flush();
		}
		// 批量删除参数项
		paramDao.deleteParameterItemsByCondition(pitemIds, param.getId());
		
		// 保存pitems集合中的参数项对象
		for(int i=0; i<param.getPitems().size(); i++) {
			ParameterItem pitem = param.getPitems().get(i);
			if(pitem != null){
				// 设置参数项修改时间
				pitem.setModifyTime(curDate);
				
				if(pitem.getId() != null) {
					// 获取临时参数项对象
					ParameterItem pitemTmp = paramDao.getParameterItemById(pitem.getId());
					// 设置参数项创建时间
					pitem.setCreateTime(pitemTmp.getCreateTime());
					// 清除Session缓存中的spvTmp对象
					sess.evict(pitemTmp);
					
				} else {
					pitem.setCreateTime(curDate);
				}
				// 设置参数项与商品参数的关联关系
				pitem.setParam(param);
				
				paramDao.saveParameterItem(pitem);
			}
		}
	}

	/**
	 * 根据ID查询商品参数信息
	 */
	public Parameter getParameterById(Long id) {

		Parameter param = paramDao.getParameterById(id);

		// 初始化参数项集合
		if (param != null) {
			Hibernate.initialize(param.getPitems());
		}

		return param;
	}
	
	/**
	 * 根据指定条件分页查询参数信息
	 */
	public Page<Parameter> getParametersByCondition(Map<String, Object> condition) {
		
		Integer pageSize = (Integer) condition.get("pageSize");
		Integer pageNumber = (Integer) condition.get("pageNumber");
		
		// 初始化商品参数分页对象
		Page<Parameter> paramPage = new Page<Parameter>(pageSize, pageNumber);
		
		// 设置分页对象的总记录数
		int totalCount = paramDao.getParameterCountByCondition(condition);
		paramPage.setTotalCount(totalCount);
		
		// 设置分页对象的请求页码
		if(paramPage.getPageNumber() < 1){
			paramPage.setPageNumber(1);
		} else if(paramPage.getPageNumber() > paramPage.getTotalPage()){
			paramPage.setPageNumber(paramPage.getTotalPage());
		}
		
		// 重新设置查询条件
		condition.remove("pageNumber");
		condition.put("startIndex", paramPage.getStartIndex());
		
		// 根据指定条件分页查询参数信息
		List<Parameter> paramList = paramDao.getParametersByCondition(condition);
		// 初始化代理对象
		for(int i=0; i<paramList.size(); i++){
			// 初始化参数项代理对象
			Hibernate.initialize(paramList.get(i).getPitems());
			// 初始化商品分类代理对象
			Hibernate.initialize(paramList.get(i).getPc());
		}
		paramPage.setList(paramList);
		
		return paramPage;
	}
	
	/**
	 * 根据商品分类ID获取参数信息
	 */
	public List<Parameter> getParametersByPcId(Long pcId) {
		
		List<Parameter> params = paramDao.getParametersByPcId(pcId);
		
		// 初始化参数项代理，并将其关联值置空
		for(int i=0; i<params.size(); i++) {
			Parameter param = params.get(i);
			param.setPc(null);
			Hibernate.initialize(param.getPitems());
			List<ParameterItem> pitems = param.getPitems();
			for(int j=0; j<pitems.size(); j++) {
				ParameterItem pitem = pitems.get(j);
				pitem.setParam(null);
				pitem.setPpitems(null);
			}
		}
		
		return params;
	}
	
	/**
	 * 根据ID集合批量删除商品参数
	 */
	public void deleteParametersByIds(Long[] ids) {
		
		// 查询待删除的商品参数
		List<Parameter> params = paramDao.getParametersByIds(ids);
		
		// 批量删除商品参数
		for(Parameter param : params) {
			deleteParameter(param);
		}
		
	}
	
	/**
	 * 删除商品参数
	 */
	public void deleteParameter(Parameter param) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 解除参数项与商品间的关联关系
		List<ParameterItem> pitems = param.getPitems();
		for(ParameterItem pitem : pitems) {
			List<ProductParamItemValue> ppitems = pitem.getPpitems();
			for(ProductParamItemValue ppitem : ppitems) {
				Product product = ppitem.getProduct();
				product.getPpitems().remove(ppitem);
				productDao.saveProduct(product);
			}
			sess.flush();
		}
		
		// 批量删除关联的参数项
		paramDao.deleteParameterItemsByParamId(param.getId());
		
		// 删除参数
		paramDao.deleteParameter(param);
	}

}
