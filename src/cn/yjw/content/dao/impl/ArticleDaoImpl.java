package cn.yjw.content.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.content.bean.Article;
import cn.yjw.content.dao.ArticleDao;

public class ArticleDaoImpl implements ArticleDao {
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 保存文章信息
	 */
	public void saveArticle(Article article) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(article);
	}
	
	/**
	 * 根据ID获取文章对象
	 */
	public Article getArticleById(Long id) {
		
		Session sess = sessionFactory.getCurrentSession();
		Article article = (Article) sess.get(Article.class, id);
		
		return article;
	}
	
	/**
	 * 获取符合条件的文章总数
	 */
	public int getArticleCountByCondition(Map<String, Object> condition) {
		
		// 获取查询的属性和属性值
		String searchProperty = (String) condition.get("searchProperty");
		String searchValue = (String) condition.get("searchValue");
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT COUNT(*) FROM Article a ");
		hql.append("WHERE 1=1 ");
		if(StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)) {
			if(searchProperty.equals("all")){
				hql.append("AND ad.title LIKE '%").append(searchValue).append("%' ");
			} else {
				hql.append("AND ad.").append(searchProperty).append(" LIKE '%")
						.append(searchValue).append("%' ");
			}
		}
		
		// 执行查询
		Query query = sess.createQuery(hql.toString());
		int count = ((Long) query.uniqueResult()).intValue();
		
		return count;
	}

	/**
	 * 按指定条件分页查询文章信息
	 */
	public List<Article> getArticlesByCondition(Map<String, Object> condition) {
		
		// 获取查询条件
		Integer startIndex = (Integer) condition.get("startIndex");
		Integer pageSize = (Integer) condition.get("pageSize");
		String searchProperty = (String) condition.get("searchProperty");
		String searchValue = (String) condition.get("searchValue");
		String orderProperty = (String) condition.get("orderProperty");
		String orderDirection = (String) condition.get("orderDirection");
		
		// 获取连接对象
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT a FROM Article a ");
		hql.append("WHERE 1=1 ");
		if(StringUtils.isNotBlank(searchProperty) && StringUtils.isNotBlank(searchValue)) {
			if(searchProperty.equals("all")){
				hql.append("AND a.title LIKE '%").append(searchValue).append("%' ");
			} else {
				hql.append("AND a.").append(searchProperty).append(" LIKE '%")
						.append(searchValue).append("%' ");
			}
		}
		hql.append("ORDER BY a.").append(orderProperty).append(" ").append(orderDirection);
		
		// 执行分页查询
		Query query = sess.createQuery(hql.toString());
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		List<Article> articles = query.list();
		
		return articles;
	}
	
	/**
	 * 根据ID集合获取文章信息
	 */
	public List<Article> getArticlesByIds(Long[] ids) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL语句
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT a FROM Article a ");
		hql.append("WHERE a.id IN (:ids) ");
		
		// 设置参数，执行查询
		Query query = sess.createQuery(hql.toString());
		query.setParameterList("ids", ids);
		List<Article> articles = query.list();
		
		return articles;
	}
	
	/**
	 * 删除文章
	 */
	public void deleteArticle(Article article) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.delete(article);
	}

}
