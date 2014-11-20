package cn.yjw.product.dao;

import java.util.List;
import java.util.Map;

import cn.yjw.product.bean.Product;
import cn.yjw.product.bean.ProductImage;
import cn.yjw.product.bean.ProductSub;

/**
 * @author yangjunwei
 */
public interface ProductDao {
	
	/**
	 * 保存商品信息
	 * @param product
	 */
	public void saveProduct(Product product);
	
	/**
	 * 保存商品图片
	 * @param pimg
	 */
	public void saveProductImage(ProductImage pimg);
	
	/**
	 * 保存子商品
	 * @param psub
	 */
	public void saveProductSub(ProductSub psub);
	
	/**
	 * 根据ID获取商品信息
	 * @param id
	 * @return
	 */
	public Product getProductById(Long id);
	
	/**
	 * 根据ID集合批量删除商品图片
	 * @param pimgIds
	 * @param productId
	 */
	public void deleteProductImagesByCondition(List<Long> pimgIds, Long productId);
	
	/**
	 * 根据ID集合批量删除子商品
	 * @param psubIds
	 * @param productId
	 */
	public void deleteProductSubsByCondition(List<Long> psubIds, Long productId);
	
	/**
	 * 根据ID获取商品图片对象
	 * @param id
	 * @return
	 */
	public ProductImage getProductImageById(Long id);
	
	/**
	 * 根据指定条件分页查询商品信息
	 * @param condition
	 * @return
	 */
	public List<Product> getProductsByCondition(Map<String, Object> condition);
	
	/**
	 * 获取符合条件的商品总数
	 * @param condition
	 * @return
	 */
	public int getProductCountByCondition(Map<String, Object> condition);
	
	/**
	 * 根据ID集合查询商品信息
	 * @param ids
	 * @return
	 */
	public List<Product> getProductsByIds(Long[] ids);
	
	/**
	 * 删除商品对象
	 * @param product
	 */
	public void deleteProduct(Product product);
	
	/**
	 * 根据商品ID删除关联的商品图片
	 * @param productId
	 */
	public void deleteProductImagesByProductId(Long productId);
	
	/**
	 * 根据商品ID删除关联的子商品
	 * @param productId
	 */
	public void deleteProductSubsByProductId(Long productId);
	
}
