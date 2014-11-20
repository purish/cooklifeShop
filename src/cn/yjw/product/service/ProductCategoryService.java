package cn.yjw.product.service;

import java.util.List;

import cn.yjw.product.bean.ProductCategory;

/**
 * @author yangjunwei
 */
public interface ProductCategoryService {
	
	/**
	 * 保存商品分类信息
	 * @param pc
	 */
	public void saveProductCategory(ProductCategory pc);
	
	/**
	 * 根据ID获取商品分类对象
	 * @param id
	 * @return
	 */
	public ProductCategory getProductCategoryById(Long id);
	
	/**
	 * 获取所有顶级商品分类对象
	 * @return
	 */
	public List<ProductCategory> getAllProductCategorys();
	
	/**
	 * 根据ID删除商品分类
	 * @param id
	 */
	public void deleteProductCategoryById(Long id);
	
}
