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

import cn.yjw.product.bean.Parameter;
import cn.yjw.product.bean.ProductCategory;
import cn.yjw.product.service.ParameterService;
import cn.yjw.product.service.ProductCategoryService;
import cn.yjw.utils.page.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author yangjunwei
 */
public class ParameterAction extends ActionSupport implements ModelDriven<Parameter> {

	private static final long serialVersionUID = -5984320912363621337L;

	private ParameterService paramService;
	
	private ProductCategoryService pcService;
	
	private Parameter param = new Parameter();
	
	public ParameterService getParamService() {
		return paramService;
	}
	public void setParamService(ParameterService paramService) {
		this.paramService = paramService;
	}
	
	public ProductCategoryService getPcService() {
		return pcService;
	}
	public void setPcService(ProductCategoryService pcService) {
		this.pcService = pcService;
	}
	
	public Parameter getParam() {
		return param;
	}
	public void setParam(Parameter param) {
		this.param = param;
	}
	
	/**
	 * 获取JavaBean模型对象
	 */
	public Parameter getModel() {
		return this.param;
	}
	
	/**
	 * 初始化编辑商品参数页面
	 * @return
	 */
	public String initSaveParameterUi() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		// 通过ID获取商品参数对象
		String idStr = request.getParameter("id");
		if(StringUtils.isNotBlank(idStr)) {
			Long id = Long.valueOf(idStr);
			this.param = paramService.getParameterById(id);
		}
		
		// 获取所有商品分类的集合
		List<ProductCategory> pcs = pcService.getAllProductCategorys();
		
		// 将操作类型和商品分类加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		String opType = request.getParameter("opType");
		vs.set("opType", opType);
		vs.set("pcs", pcs);
		
		return "initSaveParamUiSuccess";
	}

	/**
	 * 保存商品参数
	 * @return
	 */
	public String saveParameter() {
		
		if(param.getId() != null) {
			paramService.updateParameter(param);
		} else {
			paramService.addParameter(param);
		}
		
		return "saveParamSuccess";
	}
	
	/**
	 * 根据指定条件获取商品参数信息
	 * @return
	 */
	public String getParametersByCondition() {
		
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
		Page<Parameter> paramPage = paramService.getParametersByCondition(condition);
		
		// 获取值栈,并将数据加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		vs.set("paramPage", paramPage);
		
		// 回显请求数据
		Map<String, String> echoParam = new HashMap<String, String>();
		echoParam.put("searchProperty", searchProperty);
		echoParam.put("searchValue", searchValue);
		echoParam.put("orderProperty", orderProperty);
		echoParam.put("orderDirection", orderDirection);
		vs.set("echoParam", echoParam);
		
		return "getParamsByConditionSuccess";
	}
	
	/**
	 * 根据ID集合删除商品参数
	 */
	public void deleteParameterByIds() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] idsStr = request.getParameterValues("ids");
		// 批量删除品牌信息
		Long[] ids = new Long[idsStr.length];
		for (int i = 0; i < idsStr.length; i++) {
			ids[i] = Long.valueOf(idsStr[i]);
		}
		
		paramService.deleteParametersByIds(ids);
		
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
