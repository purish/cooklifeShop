package cn.yjw.product.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangjunwei
 */
public class Specification implements Serializable {

	private static final long serialVersionUID = 6302534241742962178L;
	
	private Long id;
	private String name;// 规格名称
	private Integer type;// 规格类型
	private String memo;// 备注
	private Integer order;// 优先顺序
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
	
	private List<SpecificationValue> spvs = new ArrayList<SpecificationValue>();// 关联规格值
	private List<Product> products = new ArrayList<Product>();// 关联商品
	
	
	public Specification() { }

	public Specification(Long id) {
		this.id = id;
	}
	
	public Specification(Long id, String name, String memo) {
		this.id = id;
		this.name = name;
		this.memo = memo;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
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
	
	public List<SpecificationValue> getSpvs() {
		return spvs;
	}

	public void setSpvs(List<SpecificationValue> spvs) {
		this.spvs = spvs;
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
		
		if(!(obj instanceof Specification)) {
			return false;
		}
		
		Specification sp = (Specification) obj;
		if(this.id.equals(sp.getId())) {
			return true;
		}
		
		return false;
	}

	public String toString() {
		return "Specification [createTime=" + createTime + ", id=" + id
				+ ", memo=" + memo + ", modifyTime=" + modifyTime + ", name="
				+ name + ", order=" + order + ", type=" + type + "]";
	}
}
