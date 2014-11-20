package cn.yjw.product.bean;

import java.io.Serializable;

/**
 * @author yangjunwei
 */
public class ProductParamItemValue implements Serializable {

	private static final long serialVersionUID = -5206954121239681003L;
	
	private Product product;// 关联商品
	private ParameterItem pitem;// 关联参数项
	private String value;// 参数值
	
	public ProductParamItemValue() { }

	public ProductParamItemValue(Product product, ParameterItem pitem) {
		this.product = product;
		this.pitem = pitem;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ParameterItem getPitem() {
		return pitem;
	}

	public void setPitem(ParameterItem pitem) {
		this.pitem = pitem;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 重新定义equals方法
	 */
	public boolean equals(Object obj) {
		
		if (!(obj instanceof ProductParamItemValue)) {
			return false;
		}

		ProductParamItemValue ppitem = (ProductParamItemValue) obj;
		if (this.product.equals(ppitem.getProduct())
				&& this.pitem.equals(ppitem.getPitem())
				&& this.value.equals(ppitem.getValue())) {
			
			return true;
		}
		
		
		return false;
	}

	public String toString() {
		return "ProductParamItemValue [value=" + value + "]";
	}
}
