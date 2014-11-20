package cn.yjw.content.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangjunwei
 */
public class Advertisement implements Serializable {

	private static final long serialVersionUID = 1462855794567232550L;
	
	private Long id;
	private String title;
	private Integer type;
	private String image;
	private String content;
	private String url;
	private Integer position;
	private String memo;
	private Integer order;
	private Date createTime;
	private Date modifyTime;
	
	public Advertisement() { }

	public Advertisement(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
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

	public String toString() {
		return "Advertisement [content=" + content + ", createTime="
				+ createTime + ", id=" + id + ", image=" + image + ", memo="
				+ memo + ", modifyTime=" + modifyTime + ", order=" + order
				+ ", position=" + position + ", title=" + title + ", type="
				+ type + ", url=" + url + "]";
	}
	
}
