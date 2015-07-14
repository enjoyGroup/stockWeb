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

	@Id
	private String productTypeCode;

	private String productTypeName;

	private String productTypeStatus;

	public Productype() {
	}

	public String getProductTypeCode() {
		return this.productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
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