package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the productype database table.
 * 
 */
@Embeddable
public class ProductypePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String productTypeCode;

	private String tin;

	public ProductypePK() {
	}
	public String getProductTypeCode() {
		return this.productTypeCode;
	}
	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	public String getTin() {
		return this.tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProductypePK)) {
			return false;
		}
		ProductypePK castOther = (ProductypePK)other;
		return 
			this.productTypeCode.equals(castOther.productTypeCode)
			&& this.tin.equals(castOther.tin);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.productTypeCode.hashCode();
		hash = hash * prime + this.tin.hashCode();
		
		return hash;
	}
}