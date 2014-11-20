package cn.yjw.content.dao;

import java.util.List;
import java.util.Map;

import cn.yjw.content.bean.Article;

/**
 * @author yangjunwei
 */
public interface ArticleDao {
	
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
	public List<Article> getArticlesByCondition(Map<String, Object> condition);
	
	/**
	 * 获取符合条件的文章总数
	 * @param condition
	 * @return
	 */
	public int getArticleCountByCondition(Map<String, Object> condition);
	
	/**
	 * 根据ID集合获取文章信息
	 * @param ids
	 * @return
	 */
	public List<Article> getArticlesByIds(Long[] ids);
	
	/**
	 * 删除文章
	 * @param article
	 */
	public void deleteArticle(Article article);
	
}
