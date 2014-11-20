package cn.yjw.content.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangjunwei
 */
public class Article implements Serializable {

	private static final long serialVersionUID = 7362672485949158397L;
	
	private Long id;
	private String title;// 文章标题
	private String author;// 文章作者
	private Integer type;// 文章类型
	private String image;// 文章图片
	private String content;// 文章内容
	private Boolean isPublish;// 是否发布
	private Boolean isTop;// 是否置顶
	private Integer order;// 优先顺序
	private Long hits;// 点击数
	private String seoTitle;// 页面标题
	private String seoKeywords;// 页面关键词
	private String seoDescription;// 页面描述
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
	
	private ArticleCategory ac;// 关联文章分类

	public Article() { }

	public Article(Long id) {
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public Boolean getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}

	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Long getHits() {
		return hits;
	}

	public void setHits(Long hits) {
		this.hits = hits;
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

	public ArticleCategory getAc() {
		return ac;
	}

	public void setAc(ArticleCategory ac) {
		this.ac = ac;
	}

	public String toString() {
		return "Article [author=" + author + ", content=" + content
				+ ", createTime=" + createTime + ", hits=" + hits + ", id="
				+ id + ", image=" + image + ", isPublish=" + isPublish
				+ ", isTop=" + isTop + ", modifyTime=" + modifyTime
				+ ", order=" + order + ", seoDescription=" + seoDescription
				+ ", seoKeywords=" + seoKeywords + ", seoTitle=" + seoTitle
				+ ", title=" + title + ", type=" + type + "]";
	}
	
}
