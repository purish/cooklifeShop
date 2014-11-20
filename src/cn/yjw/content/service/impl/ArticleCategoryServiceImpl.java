package cn.yjw.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.yjw.content.bean.Article;
import cn.yjw.content.bean.ArticleCategory;
import cn.yjw.content.dao.ArticleCategoryDao;
import cn.yjw.content.dao.ArticleDao;
import cn.yjw.content.service.ArticleCategoryService;

public class ArticleCategoryServiceImpl implements ArticleCategoryService {
	
	private SessionFactory sessionFactory;
	
	private ArticleCategoryDao acDao;
	
	private ArticleDao articleDao;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public ArticleCategoryDao getAcDao() {
		return acDao;
	}
	public void setAcDao(ArticleCategoryDao acDao) {
		this.acDao = acDao;
	}
	
	public ArticleDao getArticleDao() {
		return articleDao;
	}
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}
	
	/**
	 * 保存文章分类
	 */
	public void saveArticleCategory(ArticleCategory ac) {
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 根据父对象ID设置其是否存在
		if(ac.getParent() != null && ac.getParent().getId() == null) {
			ac.setParent(null);
		}
		
		// 设置分类等级和等级树
		List<Long> parentIds = new ArrayList<Long>();
		makeAcTreePath(ac.getParent(), parentIds);
		StringBuffer treePath = new StringBuffer();
		for(int i=0; i<parentIds.size(); i++) {
			treePath.append(parentIds.get(i));
			if (i < parentIds.size() - 1) {
				treePath.append(",");
			}
		}
		ac.setTreePath(treePath.toString());
		ac.setGrade(parentIds.size() + 1);
		
		// 设置文章分类修改时间
		Date curDate = new Date();
		ac.setModifyTime(curDate);
		// 设置创建时间
		if(ac.getId() != null) {
			ArticleCategory acTmp = acDao.getArticleCategoryById(ac.getId());
			ac.setCreateTime(acTmp.getCreateTime());
			sess.evict(acTmp);
		} else {
			ac.setCreateTime(curDate);
		}
		
		acDao.saveArticleCategory(ac);
	}
	
	// 递归构造文章分类等级树
	private void makeAcTreePath(ArticleCategory parent, List<Long> parentIds) {
		if(parent != null && parent.getId() != null) {
			parentIds.add(0, parent.getId());
			ArticleCategory parentTmp = acDao.getArticleCategoryById(parent.getId());
			makeAcTreePath(parentTmp.getParent(), parentIds);
		}
	}
	
	/**
	 * 根据ID获取文章分类对象
	 */
	public ArticleCategory getArticleCategoryById(Long id) {
		
		ArticleCategory ac = acDao.getArticleCategoryById(id);
		
		return ac;
	}

	/**
	 * 获取所有文章分类对象
	 */
	public List<ArticleCategory> getAllArticleCategorys() {
		
		List<ArticleCategory> acs = acDao.getAllArticleCategorys();
		
		// 初始化所有子分类对象
		for(int i=0; i<acs.size(); i++) {
			ArticleCategory ac = acs.get(i);
			initChildAcObjs(ac.getChilds());
		}
		
		return acs;
	}
	
	// 递归初始化所有子分类对象
	private void initChildAcObjs(List<ArticleCategory> childAcs) {
		
		Hibernate.initialize(childAcs);
		
		for(int i=0; i<childAcs.size(); i++) {
			ArticleCategory childAc = childAcs.get(i);
			initChildAcObjs(childAc.getChilds());
		}
	}

	/**
	 * 根据ID删除文章分类
	 */
	public void deleteArticleCategoryById(Long id) {
		
		// 获取待删除的文章分类对象
		ArticleCategory ac = acDao.getArticleCategoryById(id);
		
		// 递归删除文章分类
		deleteArticleCategoryByRecursion(ac);
	}
	
	// 递归删除文章分类
	private void deleteArticleCategoryByRecursion(ArticleCategory ac) {
		
		// 递归删除子分类
		List<ArticleCategory> childs = ac.getChilds();
		for(int i=0; i<childs.size(); i++) {
			deleteArticleCategoryByRecursion(childs.get(i));
		}
		
		Session sess = sessionFactory.getCurrentSession();
		
		// 删除关联的文章对象
		for(Article article : ac.getArticles()) {
			articleDao.deleteArticle(article);
		}
		
		// 删除文章分类
		acDao.deleteArticleCategory(ac);
	}
	
}
