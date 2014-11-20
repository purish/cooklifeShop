package cn.yjw.product.dao;

import java.util.List;
import java.util.Map;

import cn.yjw.product.bean.Brand;

/**
 * @author yangjunwei
 */
public interface BrandDao {
	
	/**
	 * 保存商品品牌信息
	 * @param brand
	 */
	public void saveBrand(Brand brand);
	
	/**
	 * 按指定条件分页查询品牌信息
	 * @return
	 */
	public List<Brand> getBrandsByCondition(Map<String, Object> condition);
	
	/**
	 * 获取符合条件的品牌总数
	 * @param condition
	 * @return
	 */
	public int getBrandCountByCondition(Map<String, Object> condition);
	
	/**
	 * 根据ID查询品牌信息
	 * @param id
	 */
	public Brand getBrandById(Long id);

	/**
	 * 获取所有品牌信息
	 * @return
	 */
	public List<Brand> getAllBrands();
	
	/**
	 * 根据ID集合获取品牌信息集合
	 * @param ids
	 * @return
	 */
	public List<Brand> getBrandsByIds(Long[] ids);
	
	/**
	 * 删除品牌对象
	 * @param brand
	 */
	public void deleteBrand(Brand brand);
	
	
}
