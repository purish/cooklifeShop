package cn.yjw.product.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.yjw.product.bean.Brand;
import cn.yjw.product.service.BrandService;
import cn.yjw.utils.page.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author yangjunwei
 */
public class BrandAction extends ActionSupport implements ModelDriven<Brand> {

	private static final long serialVersionUID = 4666265411498357381L;

	private BrandService brandService;

	private Brand brand = new Brand();

	// 上传品牌图标的文件对象
	private File logoImage;
	// 上传文件的类型
	private String logoImageContentType;
	// 上传文件的真实名称
	private String logoImageFileName;
	
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public BrandService getBrandService() {
		return brandService;
	}
	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}

	public File getLogoImage() {
		return logoImage;
	}
	public void setLogoImage(File logoImage) {
		this.logoImage = logoImage;
	}

	public String getLogoImageContentType() {
		return logoImageContentType;
	}
	public void setLogoImageContentType(String logoImageContentType) {
		this.logoImageContentType = logoImageContentType;
	}

	public String getLogoImageFileName() {
		return logoImageFileName;
	}
	public void setLogoImageFileName(String logoImageFileName) {
		this.logoImageFileName = logoImageFileName;
	}

	/**
	 * 获取JavaBean模型对象
	 */
	public Brand getModel() {
		return this.brand;
	}
	
	/**
	 * 初始化编辑品牌信息页面
	 * @return
	 */
	public String initSaveBrandUi(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		// 通过ID获取品牌对象
		String idStr = request.getParameter("id");
		if(StringUtils.isNotBlank(idStr)){
			Long id = Long.valueOf(idStr.trim());
			this.brand = brandService.getBrandById(id);
		}
		
		// 将操作类型加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		String opType = request.getParameter("opType");
		vs.set("opType", opType);
		
		return "initSaveBrandUiSuccess";
	}
	
	/**
	 * 保存品牌信息
	 * 
	 * @return
	 */
	public String saveBrand() {

		String logoPath = null;
		try {
			// 将图片数据保存到指定路径
			logoPath = this.saveLogoImages();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		// 设置品牌对象logo的url
		brand.setLogo(logoPath);
		
		// 保存品牌对象
		brandService.saveBrand(brand);

		return "saveBrandSuccess";
	}
	
	// 上传文件
	private String saveLogoImages() throws IOException {

		if (this.logoImage == null) {
			return null;
		}
		ServletContext sc = ServletActionContext.getServletContext();
		String dirPath = "/admin/files/goods_images";
		String realPath = sc.getRealPath(dirPath);
		File file = new File(realPath, this.logoImageFileName);
		FileUtils.copyFile(logoImage, file);

		return dirPath + "/" + this.logoImageFileName;
	}
	
	/**
	 * 按给定条件分页查询品牌信息
	 * 
	 * @return
	 */
	public String getBrandsByCondition() {
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
		
		Page<Brand> brandPage = brandService.getBrandsByCondition(condition);
		
		// 获取值栈,并将数据加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		vs.set("brandPage", brandPage);
		
		// 回显请求数据
		Map<String, String> echoParam = new HashMap<String, String>();
		echoParam.put("searchProperty", searchProperty);
		echoParam.put("searchValue", searchValue);
		echoParam.put("orderProperty", orderProperty);
		echoParam.put("orderDirection", orderDirection);
		vs.set("echoParam", echoParam);
		
		return "getBrandsByConditionSuccess";
	}

	/**
	 * 根据ID删除品牌信息
	 * @throws IOException 
	 */
	public void deleteBrandsByIds() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] idsStr = request.getParameterValues("ids");
		// 批量删除品牌信息
		Long[] ids = new Long[idsStr.length];
		for (int i = 0; i < idsStr.length; i++) {
			ids[i] = Long.valueOf(idsStr[i]);
		}
		brandService.deleteBrandsByIds(ids);

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
	
	/**
	 * 获取所有品牌信息
	 * @throws IOException
	 */
	public void getAllBrands() throws IOException {
		
		List<Brand> brands = brandService.getAllBrands();
		
		// 将商品参数集合转换为JSON
		JSONArray jsonArray = JSONArray.fromObject(brands);
		String brandListStr = jsonArray.toString();
		
		// 将JSON输出到客户端
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(brandListStr);
		
	}

}
