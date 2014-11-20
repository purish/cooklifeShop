package cn.yjw.product.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.yjw.product.bean.Attribute;
import cn.yjw.product.bean.ProductCategory;
import cn.yjw.product.service.AttributeService;
import cn.yjw.product.service.ProductCategoryService;
import cn.yjw.utils.page.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author yangjunwei
 */
public class AttributeAction extends ActionSupport implements ModelDriven<Attribute> {

	private static final long serialVersionUID = 2404238573936727751L;
	
	private AttributeService attrService;
	
	private ProductCategoryService pcService;
	
	private Attribute attr = new Attribute();
	
	public AttributeService getAttrService() {
		return attrService;
	}
	public void setAttrService(AttributeService attrService) {
		this.attrService = attrService;
	}
	
	public ProductCategoryService getPcService() {
		return pcService;
	}
	public void setPcService(ProductCategoryService pcService) {
		this.pcService = pcService;
	}
	
	public Attribute getAttr() {
		return attr;
	}
	public void setAttr(Attribute attr) {
		this.attr = attr;
	}
	
	/**
	 * 获取JavaBean模型对象
	 */
	public Attribute getModel() {
		return this.attr;
	}
	
	/**
	 * 初始化编辑属性页面
	 * @return
	 */
	public String initSaveAttributeUi() {
		
		HttpServletRequest request = ServletActionContext.getRequest(); 
		
		// 根据ID获取商品属性对象
		String idStr = request.getParameter("id");
		if(StringUtils.isNotBlank(idStr)) {
			Long id = Long.valueOf(idStr);
			this.attr = attrService.getAttributeById(id);
		}
		
		// 获取所有商品分类的集合
		List<ProductCategory> pcs = pcService.getAllProductCategorys();
		
		// 将操作类型和商品分类加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		String opType = request.getParameter("opType");
		vs.set("opType", opType);
		vs.set("pcs", pcs);
		
		return "initSaveAttrUiSuccess";
	}
	
	/**
	 * 保存商品属性
	 * @return
	 */
	public String saveAttribute(){
		
		if(attr.getId() != null) {
			attrService.updateAttribute(attr);
		} else{
			attrService.addAttribute(attr);
		}
		
		return "saveAttrSuccess";
	}
	
	/**
	 * 根据指定条件获取商品属性信息
	 * @return
	 */
	public String getAttributesByCondition() {
		
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
		
		// 根据指定条件查询分页信息
		Page<Attribute> attrPage = attrService.getAttributesByCondition(condition);
		
		// 获取值栈,并将数据加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		vs.set("attrPage", attrPage);
		
		// 回显请求数据
		Map<String, String> echoParam = new HashMap<String, String>();
		echoParam.put("searchProperty", searchProperty);
		echoParam.put("searchValue", searchValue);
		echoParam.put("orderProperty", orderProperty);
		echoParam.put("orderDirection", orderDirection);
		vs.set("echoParam", echoParam);
		
		return "getAttrsByConditionSuccess";
	}
	
	/**
	 * 根据ID集合删除商品属性
	 */
	public void deleteAttributesByIds() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] idsStr = request.getParameterValues("ids");
		// 批量删除品牌信息
		Long[] ids = new Long[idsStr.length];
		for (int i = 0; i < idsStr.length; i++) {
			ids[i] = Long.valueOf(idsStr[i]);
		}
		
		attrService.deleteAttributesByIds(ids);
		
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
