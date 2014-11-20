package cn.yjw.product.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.product.bean.Product;
import cn.yjw.product.bean.ProductImage;
import cn.yjw.product.bean.ProductSub;
import cn.yjw.product.dao.ProductDao;

/**
 * @author yangjunwei
 */
public class ProductDaoImpl implements ProductDao {
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 保存商品信息
	 */
	public void saveProduct(Product product) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(product);
	}
	
	/**
	 * 保存商品图片
	 */
	public void saveProductImage(ProductImage pimg) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(pimg);
	}

	/**
	 * 保存子商品
	 */
	public void saveProductSub(ProductSub psub) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(psub);
	}
	
	/**
	 * 根据ID获取商品信息
	 */
	public Product getProductById(Long id) {
		
		Session sess = sessionFactory.getCurrentSession();
		Product product = (Product) sess.get(Product.class, id); 
		
		return product;
	}
	
	/**
	 * 根据ID集合批量删除商品图片
	 */
	public void deleteProductImagesByCondition(List<Long> pimgIds, Long productId) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造批量删除语句
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE FROM ProductImage pimg ");
		hql.append("WHERE pimg.product.id=:productId ");
		if(pimgIds.size() > 0) {
			hql.append("and pimg.id NOT IN (:pimgIds) ");
		}
		
		// 设置参数，执行批量删除
		Query query = sess.createQuery(hql.toString());
		query.setLong("productId", productId);
		if(pimgIds.size() > 0) {
			query.setParameterList("pimgIds", pimgIds);
		}
		
		query.executeUpdate();
		
	}
	
	/**
	 * 根据ID集合批量删除子商品
	 */
	public void deleteProductSubsByCondition(List<Long> psubIds, Long productId) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造条件查询HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT psub FROM ProductSub psub ");
		hql.append("WHERE psub.product.id=:productId ");
		if(psubIds.size() > 0) {
			hql.append("AND psub.id NOT IN (:psubIds) ");
		}
		
		// 设置参数，执行查询
		Query query = sess.createQuery(hql.toString());
		query.setLong("productId", productId);
		if(psubIds.size() > 0) {
			query.setParameterList("psubIds", psubIds);
		}
		
		List<ProductSub> psubs = query.list();
		
		// 批量删除子商品
		for(int i=0; i<psubs.size(); i++) {
			sess.delete(psubs.get(i));
		}
		
	}
	
	/**
	 * 根据ID获取商品图片对象
	 */
	public ProductImage getProductImageById(Long id) {
		
		Session sess = sessionFactory.getCurrentSession();
		ProductImage pimg = (ProductImage) sess.get(ProductImage.class, id);
		
		return pimg;
	}
	
	/**
	 * 根据指定条件分页查询商品信息
	 */
	public List<Product> getProductsByCondition(Map<String, Object> condition) {
		
		// 获取查询条件
		Integer startIndex = (Integer) condition.get("startIndex");
		Integer pageSize = (Integer) condition.get("pageSize");
		String searchProperty = (String) condition.get("searchProperty");
		String searchValue = (String) condition.get("searchValue");
		String orderProperty = (String) condition.get("orderProperty");
		String orderDirection = (String) condition.get("orderDirection");
		
		Long productCategoryId = (Long) condition.get("productCategoryId");
		Long brandId = (Long) condition.get("brandId");
		String tagId = (String) condition.get("tagId");
		Boolean isMarketable = (Boolean) condition.get("isMarketable");
		Boolean isList = (Boolean) condition.get("isList");
		Boolean isTop = (Boolean) condition.get("isTop");
		Boolean isGift = (Boolean) condition.get("isGift");
		Boolean isOutOfStock = (Boolean) condition.get("isOutOfStock");
		
		// 获取连接对象
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL查询语句
		StringBuffer hql = new StringBuffer();
		Map<String, Object> hqlParams = new HashMap<String, Object>();
		hql.append("SELECT p FROM Product p ");
		hql.append("WHERE 1=1 ");
		// 商品分类ID
		if(productCategoryId != null) {
			hql.append("AND p.pc.id=:productCategoryId ");
			hqlParams.put("productCategoryId", productCategoryId);
		}
		// 所属品牌ID
		if(brandId != null) {
			hql.append("AND p.brand.id=:brandId ");
			hqlParams.put("brandId", brandId);
		}
		// 商品标签ID
		if(tagId != null) {
			if(tagId.equals("hot")) {
				hql.append("AND p.isHot=true ");
			} else if(tagId.equals("new")) {
				hql.append("AND p.isNew=true ");
			} else if(tagId.equals("recmd")) {
				hql.append("AND p.idRecmd=true ");
			}
		}
		// 是否上架
		if(isMarketable != null) {
			hql.append("AND p.isMarketable=:isMarketable ");
			hqlParams.put("isMarketable", isMarketable);
		}
		// 是否列出
		if(isList != null) {
			hql.append("AND p.isList=:isList ");
			hqlParams.put("isList", isList);
		}
		// 是否置顶
		if(isTop != null) {
			hql.append("AND p.isTop=:isTop ");
			hqlParams.put("isTop", isTop);
		}
		// 是否为赠品
		if(isGift != null) {
			hql.append("AND p.isGift=:isGift ");
			hqlParams.put("isGift", isGift);
		}
		// 是否缺货
		if(isOutOfStock != null) {
			if(isOutOfStock) {
				hql.append("AND p.stock<=0 ");
			} else {
				hql.append("AND p.stock>0 ");
			}
		}
		// 搜索属性和搜索值
		if(StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)){
			if(searchProperty.equals("all")){
				hql.append("AND (p.name LIKE '%").append(searchValue).append("%' ");
				hql.append("OR p.sn LIKE '%").append(searchValue).append("%') ");
			} else {
				hql.append("AND p.").append(searchProperty).append(" LIKE '%")
						.append(searchValue).append("%' ");
			}
		}
		// 排序属性和排序方向
		hql.append("ORDER BY p.").append(orderProperty).append(" ").append(orderDirection);
		
		// 设置条件参数，执行分页查询
		Query query = sess.createQuery(hql.toString());
		Iterator<Map.Entry<String, Object>> hqlParamIt = hqlParams.entrySet().iterator();
		while(hqlParamIt.hasNext()) {
			Map.Entry<String, Object> hqlParamMe = hqlParamIt.next();
			query.setParameter(hqlParamMe.getKey(), hqlParamMe.getValue());
		}
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		List<Product> products = query.list();
		
		return products;
	}
	
	/**
	 * 获取符合条件的商品总数
	 */
	public int getProductCountByCondition(Map<String, Object> condition) {
		
		// 获取查询条件
		String searchProperty = (String) condition.get("searchProperty");
		String searchValue = (String) condition.get("searchValue");
		
		Long productCategoryId = (Long) condition.get("productCategoryId");
		Long brandId = (Long) condition.get("brandId");
		String tagId = (String) condition.get("tagId");
		Boolean isMarketable = (Boolean) condition.get("isMarketable");
		Boolean isList = (Boolean) condition.get("isList");
		Boolean isTop = (Boolean) condition.get("isTop");
		Boolean isGift = (Boolean) condition.get("isGift");
		Boolean isOutOfStock = (Boolean) condition.get("isOutOfStock");
		
		// 获取连接对象
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL查询语句
		StringBuffer hql = new StringBuffer();
		Map<String, Object> hqlParams = new HashMap<String, Object>();
		hql.append("SELECT COUNT(*) FROM Product p ");
		hql.append("WHERE 1=1 ");
		// 商品分类ID
		if(productCategoryId != null) {
			hql.append("AND p.pc.id=:productCategoryId ");
			hqlParams.put("productCategoryId", productCategoryId);
		}
		// 所属品牌ID
		if(brandId != null) {
			hql.append("AND p.brand.id=:brandId ");
			hqlParams.put("brandId", brandId);
		}
		// 商品标签ID
		if(tagId != null) {
			if(tagId.equals("hot")) {
				hql.append("AND p.isHot=true ");
			} else if(tagId.equals("new")) {
				hql.append("AND p.isNew=true ");
			} else if(tagId.equals("recmd")) {
				hql.append("AND p.idRecmd=true ");
			}
		}
		// 是否上架
		if(isMarketable != null) {
			hql.append("AND p.isMarketable=:isMarketable ");
			hqlParams.put("isMarketable", isMarketable);
		}
		// 是否列出
		if(isList != null) {
			hql.append("AND p.isList=:isList ");
			hqlParams.put("isList", isList);
		}
		// 是否置顶
		if(isTop != null) {
			hql.append("AND p.isTop=:isTop ");
			hqlParams.put("isTop", isTop);
		}
		// 是否为赠品
		if(isGift != null) {
			hql.append("AND p.isGift=:isGift ");
			hqlParams.put("isGift", isGift);
		}
		// 是否缺货
		if(isOutOfStock != null) {
			if(isOutOfStock) {
				hql.append("AND p.stock<=0 ");
			} else {
				hql.append("AND p.stock>0 ");
			}
		}
		// 搜索属性和搜索值
		if(StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)){
			if(searchProperty.equals("all")){
				hql.append("AND (p.name LIKE '%").append(searchValue).append("%' ");
				hql.append("OR p.sn LIKE '%").append(searchValue).append("%') ");
			} else {
				hql.append("AND p.").append(searchProperty).append(" LIKE '%")
						.append(searchValue).append("%' ");
			}
		}
		
		// 设置条件参数，执行分页查询
		Query query = sess.createQuery(hql.toString());
		Iterator<Map.Entry<String, Object>> hqlParamIt = hqlParams.entrySet().iterator();
		while(hqlParamIt.hasNext()) {
			Map.Entry<String, Object> hqlParamMe = hqlParamIt.next();
			query.setParameter(hqlParamMe.getKey(), hqlParamMe.getValue());
		}
		int count = ((Long) query.uniqueResult()).intValue();
		
		return count;
	}
	
	/**
	 * 根据ID集合查询商品信息
	 */
	public List<Product> getProductsByIds(Long[] ids) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT p FROM Product p ");
		hql.append("WHERE p.id IN (:ids) ");
		
		// 设置参数，执行查询
		Query query = sess.createQuery(hql.toString());
		query.setParameterList("ids", ids);
		List<Product> products = query.list();
		
		return products;
	}
	
	/**
	 * 删除商品
	 */
	public void deleteProduct(Product product) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.delete(product);
	}
	
	/**
	 * 根据商品ID删除关联的商品图片
	 */
	public void deleteProductImagesByProductId(Long productId) {

		Session sess = sessionFactory.getCurrentSession();

		// 构造批量删除HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE FROM ProductImage pimg ");
		hql.append("WHERE pimg.product.id=:productId ");
		
		// 设置参数，执行删除
		Query query = sess.createQuery(hql.toString());
		query.setLong("productId", productId);
		query.executeUpdate();
	}
	
	/**
	 * 根据商品ID删除关联的子商品
	 */
	public void deleteProductSubsByProductId(Long productId) {
		
		Session sess = sessionFactory.getCurrentSession();

		// 构造HQL查询语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT psub FROM ProductSub psub ");
		hql.append("WHERE psub.product.id=:productId ");
		
		// 设置参数，执行删除
		Query query = sess.createQuery(hql.toString());
		query.setLong("productId", productId);
		List<ProductSub> psubs = query.list();
		
		// 批量删除子商品
		for(int i=0; i<psubs.size(); i++) {
			sess.delete(psubs.get(i));
		}
	}
	
}
