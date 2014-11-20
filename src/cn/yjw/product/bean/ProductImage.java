package cn.yjw.product.bean;

import java.io.Serializable;

/**
 * @author yangjunwei
 */
public class ProductImage implements Serializable {

	private static final long serialVersionUID = 3688996135327812984L;
	
	private Long id;
	private String title;// 标题
	private Integer order;// 优先顺序
	private String image;// 图片
	private Product product;// 关联商品
	
	public ProductImage() { }

	public ProductImage(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String toString() {
		return "ProductImage [id=" + id + ", image=" + image + ", order="
				+ order + ", title=" + title + "]";
	}
}
