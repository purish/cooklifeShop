package cn.yjw.product.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangjunwei
 */
public class Parameter implements Serializable {
	
	private static final long serialVersionUID = 6903603692393293330L;
	
	private Long id;
	private String name;// 参数名称
	private Integer order;// 优先顺序
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
	private List<ParameterItem> pitems = new ArrayList<ParameterItem>();// 关联参数项
	private ProductCategory pc;// 关联商品分类
	
	public Parameter() { }

	public Parameter(Long id) {
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
	
	public List<ParameterItem> getPitems() {
		return pitems;
	}

	public void setPitems(List<ParameterItem> pitems) {
		this.pitems = pitems;
	}

	public ProductCategory getPc() {
		return pc;
	}

	public void setPc(ProductCategory pc) {
		this.pc = pc;
	}
	
	/**
	 * 重新定义equals方法
	 */
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Parameter)) {
			return false;
		}
		
		Parameter param = (Parameter) obj;
		if(this.id.equals(param.getId())) {
			return true;
		}
		
		return false;
	}

	public String toString() {
		return "Parameter [createTime=" + createTime + ", id=" + id
				+ ", modifyTime=" + modifyTime + ", name=" + name + ", order="
				+ order + "]";
	}
}
