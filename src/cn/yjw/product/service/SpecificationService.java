package cn.yjw.product.service;

import java.util.List;
import java.util.Map;

import cn.yjw.product.bean.Specification;
import cn.yjw.utils.page.Page;

/**
 * @author yangjunwei
 */
public interface SpecificationService {
	
	/**
	 * 新增商品规格信息
	 * @param sp
	 */
	public void addSpecification(Specification sp);
	
	/**
	 * 修改商品规格信息
	 * @param sp
	 */
	public void updateSpecification(Specification sp);
	
	/**
	 * 根据ID查询商品规格信息
	 * @param id
	 */
	public Specification getSpecificationById(Long id);
	
	/**
	 * 根据指定条件分页查询规格信息
	 * @param condition
	 * @return
	 */
	public Page<Specification> getSpecificationsByCondition(Map<String, Object> condition);
	
	/**
	 * 根据ID集合批量删除规格信息
	 * @param ids
	 */
	public void deleteSpecificationsByIds(Long[] ids);
	
	/**
	 * 删除商品规格
	 * @param sp
	 */
	public void deleteSpecification(Specification sp);
	
	/**
	 * 获取所有规格信息
	 * @return
	 */
	public List<Specification> getAllSpecifications();
	
}
