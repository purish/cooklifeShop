package cn.yjw.product.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangjunwei
 */
public class Product implements Serializable {

	private static final long serialVersionUID = -7802448777871170574L;
	
	private Long id;
	private String name;// 商品名称
	private String sn;// 商品编号
	private Double price;// 销售价
	private Double cost;// 成本价
	private Double marketPrice;// 市场价
	private String image;// 商品图片
	private String unit;// 单位
	private Integer weight;// 重量
	private Integer stock;// 库存
	private String stockMemo;// 库存备注
	private Integer order;// 优先顺序
	private String memo;// 备注
	private String keyword;// 搜索关键词
	private String seoTitle;// 页面标题
	private String seoKeywords;// 页面关键词
	private String seoDescription;// 页面描述
	private String introduction;// 商品介绍
	private Boolean isHot;// 热销
	private Boolean isNew;// 新品
	private Boolean isRecmd;// 推荐
	private Boolean isMarketable;// 是否上架
	private Boolean isList;// 是否列出
	private Boolean isTop;// 是否置顶
	private Boolean isGift;// 是否为赠品
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
	
	private ProductCategory pc;// 关联商品分类
	private Brand brand;// 关联品牌
	private List<ProductImage> pimgs = new ArrayList<ProductImage>();// 关联商品图片
	private List<Specification> sps = new ArrayList<Specification>();// 关联规格
	private List<ProductSub> psubs = new ArrayList<ProductSub>();// 关联子商品
	private List<AttributeOption> atopts = new ArrayList<AttributeOption>();// 关联属性值
	private List<ProductParamItemValue> ppitems = new ArrayList<ProductParamItemValue>();// 关联参数值
	
	public Product() { }

	public Product(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getStockMemo() {
		return stockMemo;
	}

	public void setStockMemo(String stockMemo) {
		this.stockMemo = stockMemo;
	}
	
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public Boolean getIsRecmd() {
		return isRecmd;
	}

	public void setIsRecmd(Boolean isRecmd) {
		this.isRecmd = isRecmd;
	}
	
	public Boolean getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	public Boolean getIsList() {
		return isList;
	}

	public void setIsList(Boolean isList) {
		this.isList = isList;
	}

	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	public Boolean getIsGift() {
		return isGift;
	}

	public void setIsGift(Boolean isGift) {
		this.isGift = isGift;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public ProductCategory getPc() {
		return pc;
	}

	public void setPc(ProductCategory pc) {
		this.pc = pc;
	}
	
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	public List<ProductImage> getPimgs() {
		return pimgs;
	}

	public void setPimgs(List<ProductImage> pimgs) {
		this.pimgs = pimgs;
	}
	
	public List<Specification> getSps() {
		return sps;
	}

	public void setSps(List<Specification> sps) {
		this.sps = sps;
	}
	
	public List<ProductSub> getPsubs() {
		return psubs;
	}

	public void setPsubs(List<ProductSub> psubs) {
		this.psubs = psubs;
	}
	
	public List<AttributeOption> getAtopts() {
		return atopts;
	}

	public void setAtopts(List<AttributeOption> atopts) {
		this.atopts = atopts;
	}
	
	public List<ProductParamItemValue> getPpitems() {
		return ppitems;
	}

	public void setPpitems(List<ProductParamItemValue> ppitems) {
		this.ppitems = ppitems;
	}
	
	/**
	 * 重新定义equals方法
	 */
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Product)) {
			return false;
		}
		
		Product product = (Product) obj;
		if(this.id.equals(product.getId())) {
			return true;
		}
		
		return false;
	}

	public String toString() {
		return "Product [cost=" + cost + ", createTime=" + createTime + ", id="
				+ id + ", image=" + image + ", introduction=" + introduction
				+ ", isGift=" + isGift + ", isHot=" + isHot + ", isList="
				+ isList + ", isMarketable=" + isMarketable + ", isNew="
				+ isNew + ", isRecmd=" + isRecmd + ", isTop=" + isTop
				+ ", keyword=" + keyword + ", marketPrice=" + marketPrice
				+ ", memo=" + memo + ", modifyTime=" + modifyTime + ", name="
				+ name + ", order=" + order + ", price=" + price
				+ ", seoDescription=" + seoDescription + ", seoKeywords="
				+ seoKeywords + ", seoTitle=" + seoTitle + ", sn=" + sn
				+ ", stock=" + stock + ", stockMemo=" + stockMemo + ", unit="
				+ unit + ", weight=" + weight + "]";
	}
}
