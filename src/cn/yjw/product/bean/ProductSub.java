package cn.yjw.product.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangjunwei
 */
public class ProductSub implements Serializable {

	private static final long serialVersionUID = 6358926298396917707L;
	
	private Long id;
	private Product product;// 关联商品
	private List<SpecificationValue> spvs = new ArrayList<SpecificationValue>();// 关联规格值
	
	public ProductSub() { }

	public ProductSub(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public List<SpecificationValue> getSpvs() {
		return spvs;
	}

	public void setSpvs(List<SpecificationValue> spvs) {
		this.spvs = spvs;
	}

	public String toString() {
		return "ProductSub [id=" + id + "]";
	}
}
