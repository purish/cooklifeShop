package cn.yjw.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.product.bean.Attribute;
import cn.yjw.product.bean.AttributeOption;
import cn.yjw.product.bean.Product;
import cn.yjw.product.dao.AttributeDao;
import cn.yjw.product.dao.ProductDao;
import cn.yjw.product.service.AttributeService;
import cn.yjw.utils.page.Page;

/**
 * @author yangjunwei
 */
public class AttributeServiceImpl implements AttributeService {
	
	private SessionFactory sessionFactory;
	
	private AttributeDao attrDao;
	
	private ProductDao productDao;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public AttributeDao getAttrDao() {
		return attrDao;
	}
	public void setAttrDao(AttributeDao attrDao) {
		this.attrDao = attrDao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	/**
	 * 添加商品属性
	 */
	public void addAttribute(Attribute attr) {
		
		// 设置商品参数的修改时间和创建时间
		Date curDate = new Date();
		attr.setModifyTime(curDate);
		attr.setCreateTime(curDate);
		
		// 保存商品属性对象
		attrDao.saveAttribute(attr);
		
		// 保存商品属性值
		for(int i=0; i<attr.getAtopts().size(); i++){
			AttributeOption atopt = attr.getAtopts().get(i);
			if(atopt != null){
				// 设置属性值与属性的关联关系
				atopt.setAttr(attr);
				// 保存属性值对象
				attrDao.saveAttributeOption(atopt);
			}
		}
	}
	
	/**
	 * 更新商品属性
	 */
	public void updateAttribute(Attribute attr) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 设置商品属性的修改时间和创建时间
		Date curDate = new Date();
		attr.setModifyTime(curDate);
		// 获取临时属性对象
		Attribute attrTmp = attrDao.getAttributeById(attr.getId());
		attr.setCreateTime(attrTmp.getCreateTime());
		sess.evict(attrTmp);// 清除Session缓存中的attrTmp对象
		
		// 保存商品属性对象
		attrDao.saveAttribute(attr);
		
		// 删除不在atopts集合中的属性值对象
		List<Long> atoptIds = new ArrayList<Long>();
		for(int i=0; i<attr.getAtopts().size(); i++){
			AttributeOption atopt = attr.getAtopts().get(i);
			if(atopt != null && atopt.getId() != null){
				atoptIds.add(atopt.getId());
			}
		}
		// 获取不在atopts集合中的属性值对象
		List<AttributeOption> atopts = attrDao.getAttributeOptionsByCondition(atoptIds, attr.getId());
		// 解除属性值与商品间的关联关系
		for(int i=0; i<atopts.size(); i++) {
			AttributeOption atopt = atopts.get(i);
			List<Product> products = atopt.getProducts();
			for(int j=0; j<products.size(); j++) {
				products.get(j).getAtopts().remove(atopt);
				productDao.saveProduct(products.get(j));
			}
			sess.flush();
		}
		// 批量删除属性值
		attrDao.deleteAttributeOptionsByCondition(atoptIds, attr.getId());
		
		// 保存atopts集合中的属性值对象
		for(int i=0; i<attr.getAtopts().size(); i++){
			AttributeOption atopt = attr.getAtopts().get(i);
			if(atopt != null){
				// 设置属性值与属性的关联关系
				atopt.setAttr(attrTmp);
				
				// 保存属性值
				attrDao.saveAttributeOption(atopt);
			}
		}
	}

	/**
	 * 根据ID获取商品属性信息
	 */
	public Attribute getAttributeById(Long id) {
		
		Attribute attr = attrDao.getAttributeById(id);
		
		// 初始化属性值集合
		if(attr != null){
			Hibernate.initialize(attr.getAtopts());
		}
		
		return attr;
	}

	/**
	 * 根据指定条件分页查询商品属性信息
	 */
	public Page<Attribute> getAttributesByCondition(Map<String, Object> condition) {
		
		Integer pageSize = (Integer) condition.get("pageSize");
		Integer pageNumber = (Integer) condition.get("pageNumber");
		
		// 初始化商品属性分页对象
		Page<Attribute> attrPage = new Page<Attribute>(pageSize, pageNumber);
		
		// 设置分页对象的总记录数
		int totalCount = attrDao.getAttributeCountByCondition(condition);
		attrPage.setTotalCount(totalCount);
		
		// 设置分页对象的请求页码
		if(attrPage.getPageNumber() < 1){
			attrPage.setPageNumber(1);
		} else if(attrPage.getPageNumber() > attrPage.getTotalPage()){
			attrPage.setPageNumber(attrPage.getTotalPage());
		}
		
		// 重新设置查询条件
		condition.remove("pageNumber");
		condition.put("startIndex", attrPage.getStartIndex());
		
		// 根据指定条件分页查询属性信息
		List<Attribute> attrList = attrDao.getAttributesByCondition(condition);
		// 初始化代理对象
		for(int i=0; i<attrList.size(); i++){
			// 初始化属性值代理对象
			Hibernate.initialize(attrList.get(i).getAtopts());
			// 初始化商品分类代理对象
			Hibernate.initialize(attrList.get(i).getPc());
		}
		attrPage.setList(attrList);
		
		return attrPage;
	}
	
	/**
	 * 根据商品分类ID查询关联的属性信息
	 * @param pcId
	 * @return
	 */
	public List<Attribute> getAttributesByPcId(Long pcId) {
		
		List<Attribute> attrs = attrDao.getAttributesByPcId(pcId);
		
		// 初始化属性值代理，并将其关联值置空
		for(int i=0; i<attrs.size(); i++) {
			Attribute attr = attrs.get(i);
			attr.setPc(null);
			Hibernate.initialize(attr.getAtopts());
			List<AttributeOption> atopts = attr.getAtopts();
			for(int j=0; j<atopts.size(); j++) {
				AttributeOption atopt = atopts.get(j);
				atopt.setAttr(null);
				atopt.setProducts(null);
			}
		}
		
		return attrs;
	}
	
	/**
	 * 根据ID集合批量删除商品属性
	 */
	public void deleteAttributesByIds(Long[] ids) {
		
		// 查询待删除的商品属性
		List<Attribute> attrs = attrDao.getAttributesByIds(ids);
		
		// 批量删除商品属性
		for(Attribute attr : attrs) {
			deleteAttribute(attr);
		}
	}
	
	/**
	 * 删除商品属性
	 */
	public void deleteAttribute(Attribute attr) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 解除属性值与商品的关联关系
		List<AttributeOption> atopts = attr.getAtopts();
		for(AttributeOption atopt : atopts) {
			List<Product> products = atopt.getProducts();
			for(Product product : products) {
				product.getAtopts().remove(atopt);
				productDao.saveProduct(product);
			}
			sess.flush();
		}
		
		// 批量删除关联的属性值
		attrDao.deleteAttributeOptionsByAttrId(attr.getId());
		
		// 删除属性
		attrDao.deleteAttribute(attr);
	}

}
