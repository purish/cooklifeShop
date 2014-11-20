package cn.yjw.product.dao;

import java.util.List;
import java.util.Map;

import cn.yjw.product.bean.Attribute;
import cn.yjw.product.bean.AttributeOption;

/**
 * @author yangjunwei
 */
public interface AttributeDao {

	/**
	 * 保存商品属性
	 * @param attr
	 */
	public void saveAttribute(Attribute attr);
	
	/**
	 * 保存商品属性值
	 * @param atopt
	 */
	public void saveAttributeOption(AttributeOption atopt);
	
	/**
	 * 根据ID查询商品属性
	 * @param id
	 * @return
	 */
	public Attribute getAttributeById(Long id);
	
	/**
	 * 根据ID查询商品属性值
	 * @param id
	 * @return
	 */
	public AttributeOption getAttributeOptionById(Long id);
	
	/**
	 * 根据指定条件获取属性值集合
	 * @param atoptIds
	 * @param attrId
	 * @return
	 */
	public List<AttributeOption> getAttributeOptionsByCondition(List<Long> atoptIds, Long attrId);
	
	/**
	 * 根据指定条件批量删除属性值
	 * @param atoptIds
	 * @param attrId
	 */
	public void deleteAttributeOptionsByCondition(List<Long> atoptIds, Long attrId);
	
	/**
	 * 获取符合条件的属性总数
	 * @param condition
	 * @return
	 */
	public int getAttributeCountByCondition(Map<String, Object> condition);
	
	/**
	 * 根据指定条件分页查询商品属性
	 * @param condition
	 * @return
	 */
	public List<Attribute> getAttributesByCondition(Map<String, Object> condition);
	
	/**
	 * 根据商品分类ID查询关联的属性信息
	 * @param pcId
	 * @return
	 */
	public List<Attribute> getAttributesByPcId(Long pcId);
	
	/**
	 * 根据ID集合获取商品属性
	 * @param ids
	 * @return
	 */
	public List<Attribute> getAttributesByIds(Long[] ids);
	
	/**
	 * 根据属性ID批量删除关联的属性值
	 * @param attrId
	 */
	public void deleteAttributeOptionsByAttrId(Long attrId);
	
	/**
	 * 删除商品属性
	 * @param attr
	 */
	public void deleteAttribute(Attribute attr);
	
}
