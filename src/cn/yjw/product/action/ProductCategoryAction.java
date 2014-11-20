package cn.yjw.product.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.yjw.product.bean.Brand;
import cn.yjw.product.bean.ProductCategory;
import cn.yjw.product.service.BrandService;
import cn.yjw.product.service.ProductCategoryService;
import cn.yjw.utils.json.JsonDateValueProcessor;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author yangjunwei
 */
public class ProductCategoryAction extends ActionSupport implements ModelDriven<ProductCategory> {

	private static final long serialVersionUID = 510790771591336095L;
	
	private ProductCategoryService pcService;
	
	private BrandService brandService;
	
	private ProductCategory pc = new ProductCategory();
	
	public ProductCategoryService getPcService() {
		return pcService;
	}
	public void setPcService(ProductCategoryService pcService) {
		this.pcService = pcService;
	}
	
	public BrandService getBrandService() {
		return brandService;
	}
	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}

	public ProductCategory getPc() {
		return pc;
	}
	public void setPc(ProductCategory pc) {
		this.pc = pc;
	}

	/**
	 * 获取JavaBean模型对象
	 */
	public ProductCategory getModel() {
		return pc;
	}
	
	/**
	 * 初始化编辑商品分类页面
	 * @return
	 */
	public String initSaveProductCategoryUi(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		// 通过ID获取商品分类对象
		String idStr = request.getParameter("id");
		if(StringUtils.isNotBlank(idStr)){
			Long id = Long.valueOf(idStr);
			this.pc = pcService.getProductCategoryById(id);
		}
		
		// 获取所有商品分类的集合
		List<ProductCategory> pcs = pcService.getAllProductCategorys();
		
		// 获取所有品牌对象
		List<Brand> brands = brandService.getAllBrands();
		
		// 将商品分类和品牌数据加入值栈
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		String opType = request.getParameter("opType");
		vs.set("opType", opType);
		vs.set("brands", brands);
		vs.set("pcs", pcs);
		
		return "initSavePcUiSuccess";
	}
	
	/**
	 * 保存商品分类
	 * @return
	 */
	public String saveProductCategory(){
		
		// 获取关联的品牌ID集合，并加入到分类对象中
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] brandIds = request.getParameterValues("brandIds");
		for(int i=0; i<brandIds.length; i++) {
			Long brandId = Long.valueOf(brandIds[i]);
			Brand brand = new Brand(brandId);
			pc.getBrands().add(brand);
		}
		
		// 保存商品分类对象
		pcService.saveProductCategory(pc);
		
		return "savePcSuccess";
	}
	
	/**
	 * 根据指定条件获取商品分类信息
	 * @return
	 */
	public String getProductCategorysByCondition(){
		
		List<ProductCategory> pcs = pcService.getAllProductCategorys();
		
		// 将商品分类信息加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		vs.set("pcs", pcs);
		
		return "getPcsByConditionSuccess";
	}
	
	/**
	 * 获取全部商品分类信息
	 * @throws IOException
	 */
	public void getAllProductCategorys() throws IOException{
		
		List<ProductCategory> pcs = pcService.getAllProductCategorys();
		
		// 将商品分类集合转换为JSON
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(pcs, jsonConfig);
		String pcListStr = jsonArray.toString();
		
		// 将JSON输出到客户端
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(pcListStr);
		
	}

	/**
	 * 根据ID删除商品分类信息
	 */
	public void deleteProductCategoryById() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		Long id = Long.valueOf(request.getParameter("id"));

		pcService.deleteProductCategoryById(id);
		
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
