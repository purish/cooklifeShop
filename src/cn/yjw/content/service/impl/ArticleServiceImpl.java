package cn.yjw.content.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.content.bean.Article;
import cn.yjw.content.dao.ArticleDao;
import cn.yjw.content.service.ArticleService;
import cn.yjw.utils.page.Page;

public class ArticleServiceImpl implements ArticleService {
	
	private SessionFactory sessionFactory;
	
	private ArticleDao articleDao;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public ArticleDao getArticleDao() {
		return articleDao;
	}
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	/**
	 * 保存文章信息
	 */
	public void saveArticle(Article article) {
		
		// 获取数据库回话对象
		Session sess = sessionFactory.getCurrentSession();
		
		// 设置文章对象的修改时间
		Date curDate = new Date();
		article.setModifyTime(curDate);
		
		// 设置文章对象的创建时间、点击数和图片
		if(article.getId() != null) {
			Article articleTmp = articleDao.getArticleById(article.getId());
			article.setCreateTime(articleTmp.getCreateTime());
			article.setHits(articleTmp.getHits());
			if(article.getImage() == null) {
				article.setImage(articleTmp.getImage());
			}
			// 清除Session缓存中的articleTmp对象
			sess.evict(articleTmp);
			
		} else {
			article.setCreateTime(curDate);
			article.setHits(0L);
		}
		
		articleDao.saveArticle(article);
	}
	
	/**
	 * 根据ID获取文章对象
	 */
	public Article getArticleById(Long id) {
		
		Article article = articleDao.getArticleById(id);
		
		return article;
	}
	
	/**
	 * 按指定条件分页查询文章信息
	 */
	public Page<Article> getArticlesByCondition(Map<String, Object> condition) {
		
		Integer pageSize = (Integer) condition.get("pageSize");
		Integer pageNumber = (Integer) condition.get("pageNumber");
		
		// 初始化文章分页对象
		Page<Article> articlePage = new Page<Article>(pageSize, pageNumber);
		
		// 设置分页对象的总记录数
		int totalCount = articleDao.getArticleCountByCondition(condition);
		articlePage.setTotalCount(totalCount);
		
		// 设置分页对象的请求页码
		if(articlePage.getPageNumber() < 1){
			articlePage.setPageNumber(1);
		} else if(articlePage.getPageNumber() > articlePage.getTotalPage()){
			articlePage.setPageNumber(articlePage.getTotalPage());
		}
		
		// 重新设置查询条件
		condition.remove("pageNumber");
		condition.put("startIndex", articlePage.getStartIndex());
		
		// 根据指定条件分页查询文章信息
		List<Article> articles = articleDao.getArticlesByCondition(condition);
		// 加载文章对象中的分类信息
		for(Article article : articles) {
			Hibernate.initialize(article.getAc());
		}
		articlePage.setList(articles);
		
		return articlePage;
	}
	
	/**
	 * 根据ID集合批量删除文章信息
	 */
	public void deleteArticlesByIds(Long[] ids) {
		
		// 获取待删除的文章对象
		List<Article> articles = articleDao.getArticlesByIds(ids);
		
		// 批量删除文章信息
		for(Article article : articles) {
			articleDao.deleteArticle(article);
		}
	}
	
}
