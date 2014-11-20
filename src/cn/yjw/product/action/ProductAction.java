package cn.yjw.product.action;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.yjw.product.bean.Attribute;
import cn.yjw.product.bean.AttributeOption;
import cn.yjw.product.bean.Brand;
import cn.yjw.product.bean.Parameter;
import cn.yjw.product.bean.Product;
import cn.yjw.product.bean.ProductCategory;
import cn.yjw.product.bean.ProductImage;
import cn.yjw.product.bean.ProductSub;
import cn.yjw.product.bean.Specification;
import cn.yjw.product.bean.SpecificationValue;
import cn.yjw.product.service.AttributeService;
import cn.yjw.product.service.BrandService;
import cn.yjw.product.service.ParameterService;
import cn.yjw.product.service.ProductCategoryService;
import cn.yjw.product.service.ProductService;
import cn.yjw.product.service.SpecificationService;
import cn.yjw.utils.json.JsonDateValueProcessor;
import cn.yjw.utils.page.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author yangjunwei
 */
public class ProductAction extends ActionSupport implements ModelDriven<Product> {
	
	private static final long serialVersionUID = -3914881981219278878L;
	
	private ProductService productService;
	
	private ProductCategoryService pcService;
	
	private BrandService brandService;
	
	private SpecificationService spService;
	
	private AttributeService attrService;
	
	private ParameterService paramService;
	
	private Product product = new Product();
	
	private File coreImage;
	private String coreImageContentType;
	private String coreImageFileName;
	
	private File[] productImages;
	private String[] productImagesContentType;
	private String[] productImagesFileName;
	
	public ProductService getProductService() {
		return productService;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
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
	
	public SpecificationService getSpService() {
		return spService;
	}
	public void setSpService(SpecificationService spService) {
		this.spService = spService;
	}
	
	public AttributeService getAttrService() {
		return attrService;
	}
	public void setAttrService(AttributeService attrService) {
		this.attrService = attrService;
	}

	public ParameterService getParamService() {
		return paramService;
	}
	public void setParamService(ParameterService paramService) {
		this.paramService = paramService;
	}

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public File getCoreImage() {
		return coreImage;
	}
	public void setCoreImage(File coreImage) {
		this.coreImage = coreImage;
	}

	public String getCoreImageContentType() {
		return coreImageContentType;
	}
	public void setCoreImageContentType(String coreImageContentType) {
		this.coreImageContentType = coreImageContentType;
	}

	public String getCoreImageFileName() {
		return coreImageFileName;
	}
	public void setCoreImageFileName(String coreImageFileName) {
		this.coreImageFileName = coreImageFileName;
	}

	public File[] getProductImages() {
		return productImages;
	}
	public void setProductImages(File[] productImages) {
		this.productImages = productImages;
	}

	public String[] getProductImagesContentType() {
		return productImagesContentType;
	}
	public void setProductImagesContentType(String[] productImagesContentType) {
		this.productImagesContentType = productImagesContentType;
	}

	public String[] getProductImagesFileName() {
		return productImagesFileName;
	}
	public void setProductImagesFileName(String[] productImagesFileName) {
		this.productImagesFileName = productImagesFileName;
	}
	
	/**
	 * 获取JavaBean模型对象
	 */
	public Product getModel() {
		return this.product;
	}
	
	/**
	 * 初始化商品编辑页面
	 * @return
	 */
	public String initSaveProductUi() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		// 通过ID获取品牌对象
		String idStr = request.getParameter("id");
		if(StringUtils.isNotBlank(idStr)){
			Long id = Long.valueOf(idStr.trim());
			this.product = productService.getProductById(id);
		}
		
		// 获取所有商品分类的集合
		List<ProductCategory> pcs = pcService.getAllProductCategorys();
		
		// 获取所有品牌对象
		List<Brand> brands = brandService.getAllBrands();
		
		// 获取所有商品规格的集合
		List<Specification> allSps = spService.getAllSpecifications();
		
		// 将商品分类集合、品牌集合、操作类型加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		String opType = request.getParameter("opType");
		vs.set("opType", opType);
		vs.set("brands", brands);
		vs.set("allSps", allSps);
		vs.set("pcs", pcs);
		
		return "initSaveProductUiSuccess";
	}
	
	/**
	 * 根据商品分类ID加载商品属性
	 * @throws IOException
	 */
	public void loadAttributes() throws IOException {
		
		// 获取商品分类ID
		HttpServletRequest request = ServletActionContext.getRequest();
		String pcIdStr = request.getParameter("pcId");
		if(StringUtils.isBlank(pcIdStr)) {
			return;
		}
		Long pcId = Long.valueOf(pcIdStr);
		
		// 根据商品分类ID获取关联的商品属性集合
		List<Attribute> attrs = attrService.getAttributesByPcId(pcId);
		
		// 将商品属性集合转换为JSON
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(attrs, jsonConfig);
		String attrListStr = jsonArray.toString();
		
		// 将JSON输出到客户端
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(attrListStr);
		
	}

	/**
	 * 根据商品分类ID加载商品参数
	 * @throws IOException
	 */
	public void loadParameters() throws IOException {
		
		// 获取商品分类ID
		HttpServletRequest request = ServletActionContext.getRequest();
		String pcIdStr = request.getParameter("pcId");
		if(StringUtils.isBlank(pcIdStr)) {
			return;
		}
		Long pcId = Long.valueOf(pcIdStr);
		
		// 根据商品分类ID获取关联的商品参数集合
		List<Parameter> params = paramService.getParametersByPcId(pcId);
		
		// 将商品参数集合转换为JSON
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(params, jsonConfig);
		String paramListStr = jsonArray.toString();
		
		// 将JSON输出到客户端
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(paramListStr);
	}
	
	/**
	 * 保存商品信息
	 * @return
	 */
	public String saveProduct() {
		
		// 获取关联规格集合，并加入到商品对象中
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] spIds = request.getParameterValues("specificationIds");
		if(spIds != null) {
			for(int i=0; i<spIds.length; i++) {
				Long spId = Long.valueOf(spIds[i]);
				Specification sp = new Specification(spId);
				product.getSps().add(sp);
			}
		}
		
		// 商品图片保存目录
		String relativePath = "/admin/files/goods_images";
		
		try {
			// 上传商品展示图片
			String coreImagePath = this.saveProductImages(coreImage, relativePath, coreImageFileName);
			product.setImage(coreImagePath);
			
			// 上传商品图片
			String[] imgPaths = null;
			if(productImages != null) {
				imgPaths = new String[productImages.length];
				for(int i=0; i<productImages.length; i++) {
					imgPaths[i] = this.saveProductImages(productImages[i], relativePath, productImagesFileName[i]);
				}
			} else {
				imgPaths = new String[0];
			}
			
			// 设置商品对象的图片路径
			int imgIndex = 0;
			for(int i=0; i<product.getPimgs().size(); i++) {
				ProductImage pimg = product.getPimgs().get(i);
				if(pimg != null) {
					if(StringUtils.isBlank(pimg.getImage())) {
						pimg.setImage(null);
					} else {
						pimg.setImage(imgIndex < imgPaths.length ? imgPaths[imgIndex] : null);
						imgIndex +=1;
					}
				}
			}
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		// 设置ID为null的品牌关系
		if(product.getBrand() != null && product.getBrand().getId() == null) {
			product.setBrand(null);
		}
		// 设置ID为null的商品属性值关联关系
		for(int i=0; i<product.getAtopts().size(); i++) {
			AttributeOption adopt = product.getAtopts().get(i);
			if(adopt != null && adopt.getId() == null) {
				product.getAtopts().remove(i);
				i--;
			}
		}
		// 设置ID为null的商品规格值关联关系
		for(int i=0; i<product.getPsubs().size(); i++) {
			ProductSub psub = product.getPsubs().get(i);
			for(int j=0; j<psub.getSpvs().size(); j++) {
				SpecificationValue spv = psub.getSpvs().get(j);
				if(spv != null && spv.getId() == null) {
					psub.getSpvs().remove(j);
					j--;
				}
			}
			
		}
		
		
		// 修改或更新商品信息
		if(product.getId() != null) {
			productService.updateProduct(product);
		} else {
			productService.addProduct(product);
		}
		
		return "saveProductSuccess";
	}
	
	// 保存商品图片
	private String saveProductImages(File srcFile, String relativePath, String fileName)
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
	 * 根据指定条件分页查询商品信息
	 * @return
	 */
	public String getProductsByCondition(){
		
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
		
		String productCategoryId = request.getParameter("productCategoryId");// 商品分类ID
		String brandId = request.getParameter("brandId");// 品牌ID
		String tagId = request.getParameter("tagId");// 标签ID
		String isMarketable = request.getParameter("isMarketable");// 是否上架
		String isList = request.getParameter("isList");// 是否列出
		String isTop = request.getParameter("isTop");// 是否置顶
		String isGift = request.getParameter("isGift");// 是否为赠品
		String isOutOfStock = request.getParameter("isOutOfStock");// 是否缺货

		// 构造查询条件
		Map<String, Object> condition = new HashMap<String, Object>();
		
		condition.put("pageSize", pageSize);// 每页显示记录数
		condition.put("pageNumber", pageNumber);// 请求页码
		
		searchProperty = StringUtils.isBlank(searchProperty) ? "all" : searchProperty.trim();
		condition.put("searchProperty", searchProperty);// 搜索选项
		condition.put("searchValue", searchValue);// 搜索框
		
		orderProperty = StringUtils.isBlank(orderProperty) ? "order" : orderProperty.trim();
		condition.put("orderProperty", orderProperty);// 排序属性
		orderDirection = StringUtils.isBlank(orderDirection) ? "asc" : orderDirection.trim();
		condition.put("orderDirection", orderDirection);//排序方向
		
		condition.put("productCategoryId", StringUtils.isBlank(productCategoryId) ? 
				null : Long.valueOf(productCategoryId));// 商品分类ID
		condition.put("brandId", StringUtils.isBlank(brandId) ? null : Long.valueOf(brandId));// 品牌ID
		condition.put("tagId", StringUtils.isBlank(tagId) ? null : tagId);// 标签ID
		
		condition.put("isMarketable", StringUtils.isBlank(isMarketable) ? 
				null : Boolean.valueOf(isMarketable));// 是否上架
		condition.put("isList", StringUtils.isBlank(isList) ? null : Boolean.valueOf(isList));// 是否列出
		condition.put("isTop", StringUtils.isBlank(isTop) ? null : Boolean.valueOf(isTop));// 是否置顶
		condition.put("isGift", StringUtils.isBlank(isGift) ? null : Boolean.valueOf(isGift));// 是否为赠品
		condition.put("isOutOfStock", StringUtils.isBlank(isOutOfStock) ? 
				null : Boolean.valueOf(isOutOfStock));// 是否缺货
		
		// 根据指定条件分页查询商品信息
		Page<Product> productPage = productService.getProductsByCondition(condition);
		
		// 获取值栈,并将数据加入到值栈中
		ValueStack vs = ServletActionContext.getContext().getValueStack();
		vs.set("productPage", productPage);
		
		// 回显请求数据
		Map<String, String> echoParam = new HashMap<String, String>();
		echoParam.put("searchProperty", searchProperty);
		echoParam.put("searchValue", searchValue);
		echoParam.put("orderProperty", orderProperty);
		echoParam.put("orderDirection", orderDirection);
		echoParam.put("productCategoryId", productCategoryId);
		echoParam.put("brandId", brandId);
		echoParam.put("tagId", tagId);
		echoParam.put("isMarketable", isMarketable);
		echoParam.put("isList", isList);
		echoParam.put("isTop", isTop);
		echoParam.put("isGift", isGift);
		echoParam.put("isOutOfStock", isOutOfStock);
		vs.set("echoParam", echoParam);
		
		return "getProductsByConditionSuccess";
	}
	
	/**
	 * 根据ID集合删除商品
	 */
	public void deleteProductsByIds() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] idsStr = request.getParameterValues("ids");
		// 批量删除品牌信息
		Long[] ids = new Long[idsStr.length];
		for (int i = 0; i < idsStr.length; i++) {
			ids[i] = Long.valueOf(idsStr[i]);
		}
		
		productService.deleteProductsByIds(ids);
		
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
