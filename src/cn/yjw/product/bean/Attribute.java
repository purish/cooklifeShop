package cn.yjw.product.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangjunwei
 */
public class Attribute implements Serializable {

	private static final long serialVersionUID = 875218147973828771L;
	
	private Long id;
	private String name;// 属性名称
	private Integer order;// 优先顺序
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
	
	private List<AttributeOption> atopts = new ArrayList<AttributeOption>();// 关联属性值
	private ProductCategory pc;// 关联商品分类
	
	private Long curAtoptId; 
	
	public Attribute() { }

	public Attribute(Long id) {
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

	public ProductCategory getPc() {
		return pc;
	}

	public void setPc(ProductCategory pc) {
		this.pc = pc;
	}

	public List<AttributeOption> getAtopts() {
		return atopts;
	}

	public void setAtopts(List<AttributeOption> atopts) {
		this.atopts = atopts;
	}
	
	public Long getCurAtoptId() {
		return curAtoptId;
	}

	public void setCurAtoptId(Long curAtoptId) {
		this.curAtoptId = curAtoptId;
	}
	
	/**
	 * 重新定义equals方法
	 */
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Attribute)) {
			return false;
		}
		
		Attribute attr = (Attribute) obj;
		if(this.id.equals(attr.getId())) {
			return true;
		}
		
		return false;
	}

	public String toString() {
		return "Attribute [createTime=" + createTime + ", id=" + id
				+ ", modifyTime=" + modifyTime + ", name=" + name + ", order="
				+ order + "]";
	}
}
