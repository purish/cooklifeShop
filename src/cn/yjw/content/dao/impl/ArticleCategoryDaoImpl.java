package cn.yjw.content.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.content.bean.ArticleCategory;
import cn.yjw.content.dao.ArticleCategoryDao;

public class ArticleCategoryDaoImpl implements ArticleCategoryDao {
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 保存文章分类
	 */
	public void saveArticleCategory(ArticleCategory ac) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.saveOrUpdate(ac);
	}
	
	/**
	 * 根据ID获取文章分类对象
	 */
	public ArticleCategory getArticleCategoryById(Long id) {
		
		Session sess = sessionFactory.getCurrentSession();
		ArticleCategory ac = (ArticleCategory) sess.get(ArticleCategory.class, id);
		
		return ac;
	}
	
	/**
	 * 获取所有文章分类对象
	 */
	public List<ArticleCategory> getAllArticleCategorys() {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 构造HQL
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT ac FROM ArticleCategory ac ");
		hql.append("WHERE ac.parent.id IS NULL ");
		hql.append("ORDER BY ac.order ");
		
		// 执行查询
		Query query = sess.createQuery(hql.toString());
		List<ArticleCategory> acs = query.list();
		
		return acs;
	}
	
	/**
	 * 删除文章分类
	 */
	public void deleteArticleCategory(ArticleCategory ac) {
		
		Session sess = sessionFactory.getCurrentSession();
		sess.delete(ac);
	}

}
