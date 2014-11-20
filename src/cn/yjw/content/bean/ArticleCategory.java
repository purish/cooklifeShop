package cn.yjw.content.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangjunwei
 */
public class ArticleCategory implements Serializable {

	private static final long serialVersionUID = -8761479204075481937L;
	
	private Long id;
	private String name;// 分类名称
	private Integer grade;// 分类等级
	private String seoTitle;// 页面标题
	private String seoKeywords;// 页面关键词
	private String seoDescription;// 页面描述
	private Integer order;// 优先顺序
	private String treePath;// 等级树
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
	
	private ArticleCategory parent;// 关联上级分类
	private List<ArticleCategory> childs = new ArrayList<ArticleCategory>();// 关联下级分类
	private List<Article> articles = new ArrayList<Article>();// 关联文章

	public ArticleCategory() { }

	public ArticleCategory(Long id) {
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

	public ArticleCategory getParent() {
		return parent;
	}

	public void setParent(ArticleCategory parent) {
		this.parent = parent;
	}
	
	public List<ArticleCategory> getChilds() {
		return childs;
	}

	public void setChilds(List<ArticleCategory> childs) {
		this.childs = childs;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public String toString() {
		return "ArticleCategory [createTime=" + createTime + ", grade=" + grade
				+ ", id=" + id + ", modifyTime=" + modifyTime + ", name="
				+ name + ", order=" + order + ", seoDescription="
				+ seoDescription + ", seoKeywords=" + seoKeywords
				+ ", seoTitle=" + seoTitle + ", treePath=" + treePath + "]";
	}
	
}
