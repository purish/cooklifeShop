package cn.yjw.product.dao;

import java.util.List;
import java.util.Map;

import cn.yjw.product.bean.Parameter;
import cn.yjw.product.bean.ParameterItem;

/**
 * @author yangjunwei
 */
public interface ParameterDao {
	
	/**
	 * 保存商品参数
	 * @param param
	 */
	public void saveParameter(Parameter param);
	
	/**
	 * 保存参数项信息
	 * @param pitem
	 */
	public void saveParameterItem(ParameterItem pitem);
	
	/**
	 * 根据ID查询商品参数信息
	 * @param id
	 * @return
	 */
	public Parameter getParameterById(Long id);
	
	/**
	 * 根据ID查询商品参数项信息
	 * @param id
	 * @return
	 */
	public ParameterItem getParameterItemById(Long id);
	
	/**
	 * 根据指定条件获取参数项集合
	 * @param pitemIds
	 * @param paramId
	 * @return
	 */
	public List<ParameterItem> getParameterItemsByCondition(List<Long> pitemIds, Long paramId);
	
	/**
	 * 根据指定条件批量删除参数项
	 * @param pitemIds
	 * @param paramId
	 */
	public void deleteParameterItemsByCondition(List<Long> pitemIds, Long paramId);
	
	/**
	 * 获取符合条件的参数总数
	 * @param condition
	 * @return
	 */
	public int getParameterCountByCondition(Map<String, Object> condition);
	
	/**
	 * 根据指定条件分页查询参数信息
	 * @param condition
	 * @return
	 */
	public List<Parameter> getParametersByCondition(Map<String, Object> condition);
	
	/**
	 * 根据商品分类ID获取参数信息
	 * @param pcId
	 * @return
	 */
	public List<Parameter> getParametersByPcId(Long pcId);
	
	/**
	 * 根据ID集合获取商品参数
	 * @param ids
	 * @return
	 */
	public List<Parameter> getParametersByIds(Long[] ids);
	
	/**
	 * 根据参数ID批量删除参数项
	 * @param paramId
	 */
	public void deleteParameterItemsByParamId(Long paramId);
	
	/**
	 * 删除商品参数
	 * @param param
	 */
	public void deleteParameter(Parameter param);
	
}
