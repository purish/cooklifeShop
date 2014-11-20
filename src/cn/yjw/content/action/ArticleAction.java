package cn.yjw.content.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.yjw.content.bean.Article;
import cn.yjw.content.bean.ArticleCategory;
import cn.yjw.content.service.ArticleCategoryService;
import cn.yjw.content.service.ArticleService;
import cn.yjw.utils.page.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

public class ArticleAction extends ActionSupport implements ModelDriven<Article> {
	
	private static final long serialVersionUID = 1091318983528314874L;
	
	private ArticleService articleService;
	
	private ArticleCategoryService acService;
	
	private Article article = new Article();
	
	private File atcImage;
	private String atcImageContentType;
	private String atcImageFileName;
	
	public ArticleService getArticleService() {
		return articleService;
	}
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public ArticleCategoryService getAcService() {
		return acService;
	}
	public void setAcService(ArticleCategoryService acService) {
		this.acService = acService;
	}
	
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	
	public File getAtcImage() {
		return atcImage;
	}
	public void setAtcImage(File atcImage) {
		this.atcImage = atcImage;
	}

	public String getAtcImageContentType() {
		return atcImageContentType;
	}
	public void setAtcImageContentType(String atcImageContentType) {
		this.atcImageContentType = atcImageContentType;
	}

	public String getAtcImageFileName() {
		return atcImageFileName;
	}
	public void setAtcImageFileName(String atcImageFileName) {
		this.atcImageFileName = atcImageFileName;
	}

	/**
	 * 获取JavaBean模型对象
	 */
	public Article getModel() {
		return this.article;
	}
	
	/**
	 * 初始化编辑文章信息页面
	 * @return
	 */
	public String initSaveArticleUi() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		// 通过ID获取品牌对象
		String idStr = request.getParameter("id");
		if(StringUtils.isNotBlank(idStr)){
			Long id = Long.valueOf(idStr.trim());
			this.article = articleService.getArticleById(id);
		}
		
		// 获取所有文章分类集合
		List<ArticleCategory> acs = acService.getAllArticleCategorys();
		
		// 将文章分类集合和操作类型加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		String opType = request.getParameter("opType");
		vs.set("opType", opType);
		vs.set("acs", acs);
		
		return "initSaveArticleUiSuccess";
	}
	
	/**
	 * 保存文章信息
	 * @return
	 */
	public String saveArticle() {
		
		// 文章图片保存目录
		String relativePath = "/admin/files/goods_images";
		String atcImagePath = null;
		
		try {
			// 将文章图片保存到指定路径
			atcImagePath = this.saveArticleImages(atcImage, relativePath, atcImageFileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		// 设置文章对象的图片路径
		article.setImage(atcImagePath);
		
		// 保存文章对象
		articleService.saveArticle(article);
		
		return "saveArticleSuccess";
	}
	
	// 保存文章图片
	private String saveArticleImages(File srcFile, String relativePath, String fileName)
			throws IOException {
		
		if (srcFile == null) {
			return null;
		}
		ServletContext sc = ServletActionContext.getServletContext();
		String realPath = sc.getRealPath(relativePath);
		File destFile = new File(realPath, fileName);
		FileUtils.copyFile(srcFile, destFile);

		return relativePath + "/" + fileName;
	}
	
	/**
	 * 根据指定条件查询文章信息
	 * @return
	 */
	public String getArticlesByCondition() {
		
		// 获取请求参数
		HttpServletRequest request = ServletActionContext.getRequest();
		Integer pageSize = Integer.valueOf(StringUtils.isBlank(request.getParameter("pageSize")) ? 
				"20" : request.getParameter("pageSize").trim());// 每页显示记录数
		Integer pageNumber = Integer.valueOf(StringUtils.isBlank(request.getParameter("pageNumber")) ? 
				"1" : request.getParameter("pageNumber").trim());// 请求页码
		String searchProperty = request.getParameter("searchProperty");// 搜索选项
		String searchValue = request.getParameter("searchValue");// 搜索框
		String orderProperty = request.getParameter("orderProperty");// 排序属性
		String orderDirection = request.getParameter("orderDirection");// 排序方向

		// 构造查询条件
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("pageSize", pageSize);
		condition.put("pageNumber", pageNumber);
		searchProperty = StringUtils.isBlank(searchProperty) ? "all" : searchProperty.trim();
		condition.put("searchProperty", searchProperty);
		condition.put("searchValue", searchValue);
		orderProperty = StringUtils.isBlank(orderProperty) ? "order" : orderProperty.trim();
		condition.put("orderProperty", orderProperty);
		orderDirection = StringUtils.isBlank(orderDirection) ? "asc" : orderDirection.trim();
		condition.put("orderDirection", orderDirection);
		
		Page<Article> articlePage = articleService.getArticlesByCondition(condition);
		
		// 获取值栈,并将数据加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		vs.set("articlePage", articlePage);
		
		// 回显请求数据
		Map<String, String> echoParam = new HashMap<String, String>();
		echoParam.put("searchProperty", searchProperty);
		echoParam.put("searchValue", searchValue);
		echoParam.put("orderProperty", orderProperty);
		echoParam.put("orderDirection", orderDirection);
		vs.set("echoParam", echoParam);
		
		return "getArticlesByConditionSuccess";
	}
	
	/**
	 * 根据ID集合批量删除文章信息
	 */
	public void deleteArticlesByIds() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] idsStr = request.getParameterValues("ids");
		// 批量删除品牌信息
		Long[] ids = new Long[idsStr.length];
		for (int i = 0; i < idsStr.length; i++) {
			ids[i] = Long.valueOf(idsStr[i]);
		}
		
		articleService.deleteArticlesByIds(ids);
		
		// 构造删除成功信息的JSON串
		Map<String, String> message = new HashMap<String, String>();
		message.put("type", "success");
		message.put("content", "删除成功！！！");
		JSONObject jsonObject = JSONObject.fromObject(message);
		String msgStr = jsonObject.toString();
		
		// 将JSON输出到客户端
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");// 设置应答正文编码格式
			response.setContentType("application/json;charset=UTF-8");// 设置应答正文的内容类型
			response.getWriter().write(msgStr);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
