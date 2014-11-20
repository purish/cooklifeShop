package cn.yjw.product.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangjunwei
 */
public class Brand implements Serializable {

	private static final long serialVersionUID = 7932880015519229844L;
	
	private Long id;
	private String name;// 品牌名称
	private Integer type;// 品牌类型
	private Integer order;// 优先顺序
	private String logo;// 图标地址
	private String url;// 网址
	private String introduction;
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
	
	private List<ProductCategory> pcs = new ArrayList<ProductCategory>();// 关联商品分类
	private List<Product> products = new ArrayList<Product>();// 关联商品
	
	public Brand() { }
	
	public Brand(Long id) {
		this.id = id;
	}
	
	public Brand(Long id, String name) {
		this.id = id;
		this.name = name;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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
	
	public List<ProductCategory> getPcs() {
		return pcs;
	}

	public void setPcs(List<ProductCategory> pcs) {
		this.pcs = pcs;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	/**
	 * 重新定义equals方法
	 */
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Brand)) {
			return false;
		}
		
		Brand brand = (Brand) obj;
		if(this.id.equals(brand.getId())) {
			return true;
		}
		
		return false;
	}

	public String toString() {
		return "Brand [createTime=" + createTime + ", id=" + id
				+ ", introduction=" + introduction + ", logo=" + logo
				+ ", modifyTime=" + modifyTime + ", name=" + name + ", order="
				+ order + ", type=" + type + ", url=" + url + "]";
	}
}
