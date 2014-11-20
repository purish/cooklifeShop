package cn.yjw.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.product.bean.Attribute;
import cn.yjw.product.bean.Parameter;
import cn.yjw.product.bean.Product;
import cn.yjw.product.bean.ProductCategory;
import cn.yjw.product.dao.ProductCategoryDao;
import cn.yjw.product.service.AttributeService;
import cn.yjw.product.service.ParameterService;
import cn.yjw.product.service.ProductCategoryService;
import cn.yjw.product.service.ProductService;

/**
 * @author yangjunwei
 */
public class ProductCategoryServiceImpl implements ProductCategoryService {
	
	private SessionFactory sessionFactory;
	
	private ProductCategoryDao pcDao;
	
	private ProductService productService;
	private ParameterService paramService;
	private AttributeService attrService;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public ProductCategoryDao getPcDao() {
		return pcDao;
	}
	public void setPcDao(ProductCategoryDao pcDao) {
		this.pcDao = pcDao;
	}
	
	public ProductService getProductService() {
		return productService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	public ParameterService getParamService() {
		return paramService;
	}
	public void setParamService(ParameterService paramService) {
		this.paramService = paramService;
	}
	
	public AttributeService getAttrService() {
		return attrService;
	}
	public void setAttrService(AttributeService attrService) {
		this.attrService = attrService;
	}
	
	/**
	 * 保存商品分类信息
	 */
	public void saveProductCategory(ProductCategory pc) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 根据父对象ID设置其是否存在
		if(pc.getParent()!=null && pc.getParent().getId()==null) {
			pc.setParent(null);
		}
		
		// 设置分类等级和等级树
		List<Long> parentIds = new ArrayList<Long>();
		makePcTreePath(pc.getParent(), parentIds);
		StringBuffer treePath = new StringBuffer();
		for(int i=0; i<parentIds.size(); i++) {
			treePath.append(parentIds.get(i));
			if (i < parentIds.size() - 1) {
				treePath.append(",");
			}
		}
		pc.setTreePath(treePath.toString());
		pc.setGrade(parentIds.size() + 1);
		
		// 设置商品分类修改时间
		Date curDate = new Date();
		pc.setModifyTime(curDate);
		// 设置创建时间
		if(pc.getId() != null){
			ProductCategory pcTmp = pcDao.getProductCategoryById(pc.getId());
			pc.setCreateTime(pcTmp.getCreateTime());
			sess.evict(pcTmp);
		} else {
			pc.setCreateTime(curDate);
		}
		
		pcDao.saveProductCategory(pc);
		
	}
	
	// 递归构造商品分类等级树
	private void makePcTreePath(ProductCategory parent, List<Long> parentIds) {
		if(parent != null && parent.getId() != null) {
			parentIds.add(0, parent.getId());
			ProductCategory parentTmp = pcDao.getProductCategoryById(parent.getId());
			makePcTreePath(parentTmp.getParent(), parentIds);
		}
	}
	
	/**
	 * 根据ID获取商品分类对象
	 */
	public ProductCategory getProductCategoryById(Long id) {
		
		ProductCategory pc = pcDao.getProductCategoryById(id);
		
		// 初始化关联品牌集合
		Hibernate.initialize(pc.getBrands());
		
		return pc;
	}
	
	/**
	 * 获取所有商品分类对象
	 * @return
	 */
	public List<ProductCategory> getAllProductCategorys() {
		
		List<ProductCategory> pcs = pcDao.getAllProductCategorys();
		
		// 初始化所有子分类对象
		for (int i = 0; i < pcs.size(); i++) {
			ProductCategory pc = pcs.get(i);
			pc.setAttrs(null);
			pc.setParams(null);
			pc.setBrands(null);
			pc.setProducts(null);
			initChildPcObjs(pc.getChilds());
		}
		
		return pcs;
	}
	
	// 递归初始化所有子分类对象
	private void initChildPcObjs(List<ProductCategory> childPcs) {
		
		Hibernate.initialize(childPcs);
		
		for(int i=0;i<childPcs.size();i++){
			ProductCategory childPc = childPcs.get(i);
			childPc.setAttrs(null);
			childPc.setParams(null);
			childPc.setBrands(null);
			childPc.setProducts(null);
			childPc.setParent(null);
			initChildPcObjs(childPc.getChilds());
		}
	}
	
	/**
	 * 根据ID删除商品分类
	 */
	public void deleteProductCategoryById(Long id) {
		
		// 获取待删除的分类对象
		ProductCategory pc = pcDao.getProductCategoryById(id);
		
		// 递归删除商品分类
		deleteProductCategoryByRecursion(pc);
	}
	
	// 递归删除商品分类
	private void deleteProductCategoryByRecursion(ProductCategory pc) {
		
		// 递归删除子分类
		List<ProductCategory> childs = pc.getChilds();
		for(int i=0; i<childs.size(); i++) {
			deleteProductCategoryByRecursion(childs.get(i));
		}
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 删除关联的商品
		for(Product product : pc.getProducts()) {
			productService.deleteProduct(product);
		}
		sess.flush();
		
		// 删除关联的商品参数
		for(Parameter param : pc.getParams()) {
			paramService.deleteParameter(param);
		}
		sess.flush();
		
		// 删除关联的商品属性
		for(Attribute attr : pc.getAttrs()) {
			attrService.deleteAttribute(attr);
		}
		sess.flush();
		
		// 删除商品分类
		pcDao.deleteProductCategory(pc);
		
	}
	
}
