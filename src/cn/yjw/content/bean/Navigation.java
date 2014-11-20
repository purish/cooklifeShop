package cn.yjw.content.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangjunwei
 */
public class Navigation implements Serializable {

	private static final long serialVersionUID = 5732779324582694150L;
	
	private Long id;
	private String name;
	private Integer position;
	private String url;
	private String memo;
	private Integer order;
	private Boolean isBlank;
	private Date createTime;
	private Date modifyTime;
	
	public Navigation() { }

	public Navigation(Long id) {
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

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public Boolean getIsBlank() {
		return isBlank;
	}

	public void setIsBlank(Boolean isBlank) {
		this.isBlank = isBlank;
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

	public String toString() {
		return "Navigation [createTime=" + createTime + ", id=" + id
				+ ", isBlank=" + isBlank + ", memo=" + memo + ", modifyTime="
				+ modifyTime + ", name=" + name + ", order=" + order
				+ ", position=" + position + ", url=" + url + "]";
	}
	
}
