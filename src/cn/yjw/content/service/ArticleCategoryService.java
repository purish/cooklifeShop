package cn.yjw.content.service;

import java.util.List;

import cn.yjw.content.bean.ArticleCategory;

/**
 * @author yangjunwei
 */
public interface ArticleCategoryService {

	/**
	 * 保存文章分类
	 * @param ac
	 */
	public void saveArticleCategory(ArticleCategory ac);
	
	/**
	 * 根据ID获取文章分类对象
	 * @param id
	 * @return
	 */
	public ArticleCategory getArticleCategoryById(Long id);
	
	/**
	 * 获取所有文章分类对象
	 * @return
	 */
	public List<ArticleCategory> getAllArticleCategorys();
	
	/**
	 * 根据ID删除文章分类
	 * @param id
	 */
	public void deleteArticleCategoryById(Long id);
	
}
