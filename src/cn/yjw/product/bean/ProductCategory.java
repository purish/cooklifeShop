package cn.yjw.product.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangjunwei
 */
public class ProductCategory implements Serializable {

	private static final long serialVersionUID = -1256300516197020168L;
	
	private Long id;
	private String name;// 分类名称
	private Integer grade;// 分类等级
	private String seoTitle;// 页面标题
	private String seoKeywords;// 页面关键词
	private String seoDescription;// 页面描述
	private Integer order;// 优先顺序
	private String treePath;// 等级树
	private Boolean isHot;// 热销商品
	private Boolean isNew;// 新品上市
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
	
	private ProductCategory parent;// 关联上级分类
	private List<ProductCategory> childs = new ArrayList<ProductCategory>();// 关联下级分类
	private List<Brand> brands = new ArrayList<Brand>();// 关联品牌
	private List<Parameter> params = new ArrayList<Parameter>();// 关联商品参数
	private List<Attribute> attrs = new ArrayList<Attribute>();// 关联商品属性
	
	private List<Product> products = new ArrayList<Product>();// 关联商品
	
	
	public ProductCategory() { }

	public ProductCategory(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
	
	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public ProductCategory getParent() {
		return parent;
	}

	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}
	
	public List<ProductCategory> getChilds() {
		return childs;
	}

	public void setChilds(List<ProductCategory> childs) {
		this.childs = childs;
	}

	public List<Brand> getBrands() {
		return brands;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	public List<Parameter> getParams() {
		return params;
	}

	public void setParams(List<Parameter> params) {
		this.params = params;
	}

	public List<Attribute> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<Attribute> attrs) {
		this.attrs = attrs;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String toString() {
		return "ProductCategory [createTime=" + createTime + ", grade=" + grade
				+ ", id=" + id + ", isHot=" + isHot + ", isNew=" + isNew
				+ ", modifyTime=" + modifyTime + ", name=" + name + ", order="
				+ order + ", seoDescription=" + seoDescription
				+ ", seoKeywords=" + seoKeywords + ", seoTitle=" + seoTitle
				+ ", treePath=" + treePath + "]";
	}
}
