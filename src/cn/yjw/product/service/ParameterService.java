package cn.yjw.product.service;

import java.util.List;
import java.util.Map;

import cn.yjw.product.bean.Parameter;
import cn.yjw.utils.page.Page;

/**
 * @author yangjunwei
 */
public interface ParameterService {
	
	/**
	 * 添加商品参数
	 * @param param
	 */
	public void addParameter(Parameter param);
	
	/**
	 * 更新商品参数
	 * @param param
	 */
	public void updateParameter(Parameter param);
	
	
	/**
	 * 根据ID查询商品参数信息
	 * @param id
	 * @return
	 */
	public Parameter getParameterById(Long id);
	
	/**
	 * 根据指定条件分页查询参数信息
	 * @param condition
	 * @return
	 */
	public Page<Parameter> getParametersByCondition(Map<String, Object> condition);
	
	/**
	 * 根据商品分类ID获取参数信息
	 * @param pcId
	 * @return
	 */
	public List<Parameter> getParametersByPcId(Long pcId);
	
	/**
	 * 根据ID集合批量删除商品参数
	 * @param ids
	 */
	public void deleteParametersByIds(Long[] ids);
	
	/**
	 * 删除商品参数
	 * @param param
	 */
	public void deleteParameter(Parameter param);
	
}
