package cn.yjw.product.dao;

import java.util.List;

import cn.yjw.product.bean.ProductCategory;

/**
 * @author yangjunwei
 */
public interface ProductCategoryDao {
	
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
	 * 删除商品分类
	 * @param pc
	 */
	public void deleteProductCategory(ProductCategory pc);
	
}
