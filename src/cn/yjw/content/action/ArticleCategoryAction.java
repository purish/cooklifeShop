package cn.yjw.content.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.yjw.content.bean.ArticleCategory;
import cn.yjw.content.service.ArticleCategoryService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

public class ArticleCategoryAction extends ActionSupport implements
		ModelDriven<ArticleCategory> {

	private static final long serialVersionUID = 1226030499162110891L;
	
	private ArticleCategoryService acService;
	
	private ArticleCategory ac = new ArticleCategory();
	
	public ArticleCategoryService getAcService() {
		return acService;
	}
	public void setAcService(ArticleCategoryService acService) {
		this.acService = acService;
	}

	public ArticleCategory getAc() {
		return ac;
	}
	public void setAc(ArticleCategory ac) {
		this.ac = ac;
	}
	
	/**
	 * 获取JavaBean模型对象
	 */
	public ArticleCategory getModel() {
		return this.ac;
	}
	
	/**
	 * 初始化编辑商品分类页面
	 * @return
	 */
	public String initSaveArticleCategoryUi() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		// 通过ID获取文章分类对象
		String idStr = request.getParameter("id");
		if(StringUtils.isNotBlank(idStr)) {
			Long id = Long.valueOf(idStr);
			this.ac = acService.getArticleCategoryById(id);
		}
		
		// 获取所有文章分类集合
		List<ArticleCategory> acs = acService.getAllArticleCategorys();
		
		// 将操作类型和文章分类数据加入值栈
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		String opType = request.getParameter("opType");
		vs.set("opType", opType);
		vs.set("acs", acs);
		
		return "initSaveAcUiSuccess";
	}
	
	/**
	 * 保存文章分类
	 * @return
	 */
	public String saveArticleCategory() {
		
		acService.saveArticleCategory(ac);
		
		return "saveAcSuccess";
	}
	
	/**
	 * 根据指定条件查询文章分类
	 * @return
	 */
	public String getArticleCategorysByCondition() {
		
		List<ArticleCategory> acs = acService.getAllArticleCategorys();
		
		// 将文章分类信息加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		vs.set("acs", acs);
		
		return "getAcsByConditionSuccess";
	}
	
	/**
	 * 根据ID删除文章信息
	 */
	public void deleteArticleCategoryById() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Long id = Long.valueOf(request.getParameter("id"));

//		acService.
		
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
