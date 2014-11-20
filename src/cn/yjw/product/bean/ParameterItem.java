package cn.yjw.product.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangjunwei
 */
public class ParameterItem implements Serializable {
	
	private static final long serialVersionUID = -8148490292675844547L;
	
	private Long id;
	private String name;
	private Integer order;
	private Date createTime;
	private Date modifyTime;
	
	private Parameter param;// 关联参数
	private List<ProductParamItemValue> ppitems = new ArrayList<ProductParamItemValue>();// 关联参数值
	
	private String curPitemVal;// 参数项的当前取值
	
	public ParameterItem() { }

	public ParameterItem(Long id) {
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

	public Parameter getParam() {
		return param;
	}

	public void setParam(Parameter param) {
		this.param = param;
	}
	
	public List<ProductParamItemValue> getPpitems() {
		return ppitems;
	}

	public void setPpitems(List<ProductParamItemValue> ppitems) {
		this.ppitems = ppitems;
	}
	
	public String getCurPitemVal() {
		return curPitemVal;
	}

	public void setCurPitemVal(String curPitemVal) {
		this.curPitemVal = curPitemVal;
	}
	
	/**
	 * 重新定义equals方法
	 */
	public boolean equals(Object obj) {
		
		if(!(obj instanceof ParameterItem)) {
			return false;
		}
		
		ParameterItem pitem = (ParameterItem) obj;
		if(this.id.equals(pitem.getId())) {
			return true;
		}
		
		return false;
	}

	public String toString() {
		return "ParameterItem [createTime=" + createTime + ", id=" + id
				+ ", modifyTime=" + modifyTime + ", name=" + name + ", order="
				+ order + "]";
	}
}
