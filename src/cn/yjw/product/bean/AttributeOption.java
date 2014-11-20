package cn.yjw.product.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangjunwei
 */
public class AttributeOption implements Serializable {

	private static final long serialVersionUID = 8544472693025775720L;
	
	private Long id;
	private String option;// 商品属性值
	private Attribute attr;// 关联商品属性
	private List<Product> products = new ArrayList<Product>();// 关联商品
	
	public AttributeOption() { }

	public AttributeOption(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public Attribute getAttr() {
		return attr;
	}

	public void setAttr(Attribute attr) {
		this.attr = attr;
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	/**
	 * 重新定义equals方法
	 */
	public boolean equals(Object obj) {
		
		if(!(obj instanceof AttributeOption)) {
			return false;
		}
		
		AttributeOption atopt = (AttributeOption) obj;
		if(this.id.equals(atopt.getId())) {
			return true;
		}
		
		return false;
	}

	public String toString() {
		return "AttributeOption [id=" + id + ", option=" + option + "]";
	}
}
