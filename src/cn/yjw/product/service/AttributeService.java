package cn.yjw.product.service;

import java.util.List;
import java.util.Map;

import cn.yjw.product.bean.Attribute;
import cn.yjw.utils.page.Page;

/**
 * @author yangjunwei
 */
public interface AttributeService {
	
	/**
	 * 添加商品属性
	 * @param attr
	 */
	public void addAttribute(Attribute attr);
	
	/**
	 * 更新商品属性
	 * @param attr
	 */
	public void updateAttribute(Attribute attr);
	
	/**
	 * 根据ID获取商品属性信息
	 * @param id
	 * @return
	 */
	public Attribute getAttributeById(Long id);
	
	/**
	 * 根据指定条件分页查询商品属性信息
	 * @param condition
	 * @return
	 */
	public Page<Attribute> getAttributesByCondition(Map<String, Object> condition);
	
	/**
	 * 根据商品分类ID查询关联的属性信息
	 * @param pcId
	 * @return
	 */
	public List<Attribute> getAttributesByPcId(Long pcId);
	
	/**
	 * 根据ID集合批量删除商品属性
	 * @param ids
	 */
	public void deleteAttributesByIds(Long[] ids);
	
	/**
	 * 删除商品属性
	 * @param attr
	 */
	public void deleteAttribute(Attribute attr);
	
}
