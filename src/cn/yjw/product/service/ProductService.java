package cn.yjw.product.service;

import java.util.Map;

import cn.yjw.product.bean.Product;
import cn.yjw.utils.page.Page;

/**
 * @author yangjunwei
 */
public interface ProductService {
	
	/**
	 * 新增商品信息
	 * @param product
	 */
	public void addProduct(Product product);
	
	/**
	 * 更新商品信息
	 * @param product
	 */
	public void updateProduct(Product product);
	
	/**
	 * 根据ID获取商品信息
	 * @param id
	 * @return
	 */
	public Product getProductById(Long id);
	
	/**
	 * 根据指定条件分页查询商品信息
	 * @param condition
	 * @return
	 */
	public Page<Product> getProductsByCondition(Map<String, Object> condition);
	
	/**
	 * 根据ID集合批量删除商品
	 * @param ids
	 */
	public void deleteProductsByIds(Long[] ids);
	
	/**
	 * 删除商品
	 * @param product
	 */
	public void deleteProduct(Product product);
	
	
}
