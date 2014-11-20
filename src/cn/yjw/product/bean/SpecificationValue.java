package cn.yjw.product.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangjunwei
 */
public class SpecificationValue implements Serializable {

	private static final long serialVersionUID = 3003571866334005961L;
	
	private Long id;
	private String name;// 规格值名称
	private String image;// 规格值图片
	private Integer order;// 规格值优先顺序
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
	
	private Specification sp;// 关联规格对象
	private List<ProductSub> psubs = new ArrayList<ProductSub>();// 关联子商品
	
	public SpecificationValue() { }

	public SpecificationValue(Long id) {
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public Specification getSp() {
		return sp;
	}
	
	public void setSp(Specification sp) {
		this.sp = sp;
	}
	
	public List<ProductSub> getPsubs() {
		return psubs;
	}

	public void setPsubs(List<ProductSub> psubs) {
		this.psubs = psubs;
	}
	
	/**
	 * 重新定义equals方法
	 */
	public boolean equals(Object obj) {
		
		if(!(obj instanceof SpecificationValue)) {
			return false;
		}
		
		SpecificationValue spv = (SpecificationValue) obj;
		if(this.id.equals(spv.getId())) {
			return true;
		}
		
		return false;
	}

	public String toString() {
		return "SpecificationValue [createTime=" + createTime + ", id=" + id
				+ ", image=" + image + ", modifyTime=" + modifyTime + ", name="
				+ name + ", order=" + order + "]";
	}
}
