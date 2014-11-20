package cn.yjw.product.dao;

import java.util.List;
import java.util.Map;

import cn.yjw.product.bean.Specification;
import cn.yjw.product.bean.SpecificationValue;

/**
 * @author yangjunwei
 */
public interface SpecificationDao {
	
	/**
	 * 保存商品规格信息
	 * @param sp
	 */
	public void saveSpecification(Specification sp);
	
	/**
	 * 保存商品规格值信息
	 * @param spv
	 */
	public void saveSpecificationValue(SpecificationValue spv);
	
	/**
	 * 根据ID获取商品规格对象
	 * @param id
	 * @return
	 */
	public Specification getSpecificationById(Long id);
	
	/**
	 * 根据ID获取商品规格值对象
	 * @param id
	 */
	public SpecificationValue getSpecificationValueById(Long id);
	
	/**
	 * 根据指定条件获取规格值信息
	 * @param spvIds
	 * @param spId
	 * @return
	 */
	public List<SpecificationValue> getSpecificationValuesByCondition(List<Long> spvIds, Long spId);
	
	/**
	 * 根据指定条件，批量删除规格值
	 * @param spvIds
	 * @param spId
	 */
	public void deleteSpecificationValuesByCondition(List<Long> spvIds, Long spId);
	
	/**
	 * 获取符合条件的规格总数
	 * @param condition
	 * @return
	 */
	public int getSpecificationCountByCondition(Map<String, Object> condition);
	
	/**
	 * 根据指定条件分页查询规格信息
	 * @param condition
	 * @return
	 */
	public List<Specification> getSpecificationsByCondition(Map<String, Object> condition);
	
	/**
	 * 根据规格ID集合获取规格对象
	 * @param ids
	 * @return
	 */
	public List<Specification> getSpecificationsByIds(Long[] ids);
	
	/**
	 * 根据规格ID删除所有关联的规格值
	 * @param spId
	 */
	public void deleteSpecificationValuesBySpId(Long spId);
	
	/**
	 * 删除规格对象
	 * @param sp
	 */
	public void deleteSpecification(Specification sp);
	
	/**
	 * 获取所有规格信息
	 * @return
	 */
	public List<Specification> getAllSpecifications();
	
}
