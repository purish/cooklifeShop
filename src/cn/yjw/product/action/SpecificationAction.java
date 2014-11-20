package cn.yjw.product.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.yjw.product.bean.Specification;
import cn.yjw.product.bean.SpecificationValue;
import cn.yjw.product.service.SpecificationService;
import cn.yjw.utils.page.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author yangjunwei
 */
public class SpecificationAction extends ActionSupport implements ModelDriven<Specification> {

	private static final long serialVersionUID = -6039690632371838723L;
	
	private SpecificationService spService;
	
	private Specification sp = new Specification();
	
	private File[] spvImages;
	private String[] spvImagesContentType;
	private String[] spvImagesFileName;
	
	public SpecificationService getSpService() {
		return spService;
	}
	public void setSpService(SpecificationService spService) {
		this.spService = spService;
	}
	
	public Specification getSp() {
		return sp;
	}
	public void setSp(Specification sp) {
		this.sp = sp;
	}

	public File[] getSpvImages() {
		return spvImages;
	}
	public void setSpvImages(File[] spvImages) {
		this.spvImages = spvImages;
	}

	public String[] getSpvImagesContentType() {
		return spvImagesContentType;
	}
	public void setSpvImagesContentType(String[] spvImagesContentType) {
		this.spvImagesContentType = spvImagesContentType;
	}

	public String[] getSpvImagesFileName() {
		return spvImagesFileName;
	}
	public void setSpvImagesFileName(String[] spvImagesFileName) {
		this.spvImagesFileName = spvImagesFileName;
	}
	
	/**
	 * 获取JavaBean模型对象
	 */
	public Specification getModel() {
		return this.sp;
	}
	
	/**
	 * 初始化编辑商品规格信息的页面
	 * 
	 * @return
	 */
	public String initSaveSpecificationUi(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		// 通过ID获取商品规格信息
		String idStr = request.getParameter("id");
		if(StringUtils.isNotBlank(idStr)){
			Long id = Long.valueOf(idStr);
			this.sp = spService.getSpecificationById(id);
		}
		
		// 将操作类型加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		String opType = request.getParameter("opType");
		vs.set("opType", opType);
		
		return "initSaveSpUiSuccess";
	}

	/**
	 * 保存商品规格
	 * 
	 * @return
	 */
	public String saveSpecification() {
		
		String relativePath = "/admin/files/goods_images";
		
		try {
			// 上传规格值图片
			String[] imgPaths = null;
			if(spvImages != null){
				imgPaths = new String[spvImages.length];
				for (int i = 0; i < spvImages.length; i++) {
					imgPaths[i] = this.saveSpvImages(spvImages[i], relativePath, spvImagesFileName[i]);
				}
			} else{
				imgPaths = new String[0];
			}

			// 设置规格值的图片路径
			int imgIndex = 0;
			for (int i = 0; i < sp.getSpvs().size(); i++) {
				SpecificationValue spv = sp.getSpvs().get(i);
				if (spv != null) {
					if (StringUtils.isBlank(spv.getImage())) {
						spv.setImage(null);
					} else {
						spv.setImage(imgIndex < imgPaths.length ? imgPaths[imgIndex] : null);
						imgIndex += 1;
					}
					spv.setSp(sp);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		// 修改或新增规格信息
		if(sp.getId() != null) {
			spService.updateSpecification(sp);
		} else {
			spService.addSpecification(sp);
		}
		
		return "saveSpSuccess";
	}
	
	// 保存规格值图片
	private String saveSpvImages(File srcFile, String relativePath, String fileName)
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
	 * 按指定条件分页查询规格信息
	 * 
	 * @return
	 */
	public String getSpecificationsByCondition() {
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
		Page<Specification> spPage = spService.getSpecificationsByCondition(condition);
		
		// 获取值栈,并将数据加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		vs.set("spPage", spPage);
		
		// 回显请求数据
		Map<String, String> echoParam = new HashMap<String, String>();
		echoParam.put("searchProperty", searchProperty);
		echoParam.put("searchValue", searchValue);
		echoParam.put("orderProperty", orderProperty);
		echoParam.put("orderDirection", orderDirection);
		vs.set("echoParam", echoParam);
		
		return "getSpsByConditionSuccess";
	}
	
	/**
	 * 根据ID集合批量删除规格信息
	 * @throws IOException 
	 */
	public void deleteSpecificationsByIds() {

		HttpServletRequest request = ServletActionContext.getRequest();
		String[] idsStr = request.getParameterValues("ids");

		// 批量删除规格信息
		Long[] ids = new Long[idsStr.length];
		for (int i = 0; i < idsStr.length; i++) {
			ids[i] = Long.valueOf(idsStr[i]);
		}
		spService.deleteSpecificationsByIds(ids);
		
		// 构造删除成功信息的JSON
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
