package cn.yjw.content.service;

import java.util.Map;

import cn.yjw.content.bean.Article;
import cn.yjw.utils.page.Page;

/**
 * @author yangjunwei
 */
public interface ArticleService {
	
	/**
	 * 保存文章信息
	 * @param article
	 */
	public void saveArticle(Article article);
	
	/**
	 * 根据ID获取文章对象
	 * @param id
	 * @return
	 */
	public Article getArticleById(Long id);
	
	/**
	 * 按指定条件分页查询文章信息
	 * @param condition
	 * @return
	 */
	public Page<Article> getArticlesByCondition(Map<String, Object> condition);
	
	/**
	 * 根据ID集合批量删除文章信息
	 * @param ids
	 */
	public void deleteArticlesByIds(Long[] ids);
	
}
