package cn.yjw.product.service;

import java.util.List;
import java.util.Map;

import cn.yjw.product.bean.Brand;
import cn.yjw.utils.page.Page;

/**
 * @author yangjunwei
 */
public interface BrandService {
	
	/**
	 * 保存商品品牌信息
	 * @param brand
	 */
	public void saveBrand(Brand brand);
	
	/**
	 * 按指定条件分页查询品牌信息
	 * @return
	 */
	public Page<Brand> getBrandsByCondition(Map<String, Object> condition);
	
	/**
	 * 根据ID查询品牌信息
	 * @param id
	 */
	public Brand getBrandById(Long id);

	/**
	 * 根据ID集合批量删除品牌信息
	 * @param ids
	 */
	public void deleteBrandsByIds(Long[] ids);
	
	/**
	 * 删除品牌信息
	 * @param brand
	 */
	public void deleteBrand(Brand brand);
	
	/**
	 * 获取所有品牌信息
	 * @return
	 */
	public List<Brand> getAllBrands();
}
