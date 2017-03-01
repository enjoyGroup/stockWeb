package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the productype database table.
 * 
 */
@Entity
@NamedQuery(name="Productype.findAll", query="SELECT p FROM Productype p")
public class Productype implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProductypePK id;

	private String productTypeCodeDis;

	private String productTypeName;

	private String productTypeStatus;

	public Productype() {
	}

	public ProductypePK getId() {
		return this.id;
	}

	public void setId(ProductypePK id) {
		this.id = id;
	}

	public String getProductTypeCodeDis() {
		return this.productTypeCodeDis;
	}

	public void setProductTypeCodeDis(String productTypeCodeDis) {
		this.productTypeCodeDis = productTypeCodeDis;
	}

	public String getProductTypeName() {
		return this.productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getProductTypeStatus() {
		return this.productTypeStatus;
	}

	public void setProductTypeStatus(String productTypeStatus) {
		this.productTypeStatus = productTypeStatus;
	}

}