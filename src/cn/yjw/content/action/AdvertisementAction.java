package cn.yjw.content.action;

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

import cn.yjw.content.bean.Advertisement;
import cn.yjw.content.service.AdvertisementService;
import cn.yjw.utils.page.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author yangjunwei
 */
public class AdvertisementAction extends ActionSupport implements ModelDriven<Advertisement> {
	
	private static final long serialVersionUID = -6298350095940521811L;

	private AdvertisementService adService;
	
	private Advertisement ad = new Advertisement();
	
	private File adImage;
	private String adImageContentType;
	private String adImageFileName;
	
	public AdvertisementService getAdService() {
		return adService;
	}
	public void setAdService(AdvertisementService adService) {
		this.adService = adService;
	}

	public Advertisement getAd() {
		return ad;
	}
	public void setAd(Advertisement ad) {
		this.ad = ad;
	}

	public File getAdImage() {
		return adImage;
	}
	public void setAdImage(File adImage) {
		this.adImage = adImage;
	}

	public String getAdImageContentType() {
		return adImageContentType;
	}
	public void setAdImageContentType(String adImageContentType) {
		this.adImageContentType = adImageContentType;
	}

	public String getAdImageFileName() {
		return adImageFileName;
	}
	public void setAdImageFileName(String adImageFileName) {
		this.adImageFileName = adImageFileName;
	}
	
	/**
	 * 获取JavaBean模型对象
	 */
	public Advertisement getModel() {
		return this.ad;
	}
	
	/**
	 * 初始化编辑商品信息页面
	 * @return
	 */
	public String initSaveAdvertisementUi() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		// 通过ID获取品牌对象
		String idStr = request.getParameter("id");
		if(StringUtils.isNotBlank(idStr)){
			Long id = Long.valueOf(idStr.trim());
			this.ad = adService.getAdvertisementById(id);
		}
		
		// 将文章分类集合和操作类型加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		String opType = request.getParameter("opType");
		vs.set("opType", opType);
		
		return "initSaveAdUiSuccess";
	}
	
	/**
	 * 保存广告信息
	 * @return
	 */
	public String saveAdvertisement() {
		
		// 文章图片保存目录
		String relativePath = "/admin/files/goods_images";
		String adImagePath = null;
		
		try {
			adImagePath = saveAdvertisementImages(adImage, relativePath, adImageFileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		// 设置广告对象的图片路径
		ad.setImage(adImagePath);
		
		// 保存广告对象
		adService.saveAdvertisement(ad);
		
		return "saveAdSuccess";
	}
	
	// 保存文章图片
	private String saveAdvertisementImages(File srcFile, String relativePath, String fileName)
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
	 * 根据条件分页查询广告信息
	 * @return
	 */
	public String getAdvertisementsByCondition() {
		
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
		
		Page<Advertisement> adPage = adService.getAdvertisementsByCondition(condition);
		
		// 获取值栈,并将数据加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		vs.set("adPage", adPage);
		
		// 回显请求数据
		Map<String, String> echoParam = new HashMap<String, String>();
		echoParam.put("searchProperty", searchProperty);
		echoParam.put("searchValue", searchValue);
		echoParam.put("orderProperty", orderProperty);
		echoParam.put("orderDirection", orderDirection);
		vs.set("echoParam", echoParam);
		
		return "getAdsByConditionSuccess";
	}
	
	/**
	 * 根据ID集合批量删除广告
	 */
	public void deleteAdvertisementsByIds() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] idsStr = request.getParameterValues("ids");
		// 批量删除品牌信息
		Long[] ids = new Long[idsStr.length];
		for (int i = 0; i < idsStr.length; i++) {
			ids[i] = Long.valueOf(idsStr[i]);
		}
		
		adService.deleteAdvertisementsByIds(ids);
		
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
