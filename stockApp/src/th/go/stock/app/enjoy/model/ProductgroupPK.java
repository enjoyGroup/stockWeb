package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the productgroup database table.
 * 
 */
@Embeddable
public class ProductgroupPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String productTypeCode;

	private String productGroupCode;

	public ProductgroupPK() {
	}
	public String getProductTypeCode() {
		return this.productTypeCode;
	}
	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	public String getProductGroupCode() {
		return this.productGroupCode;
	}
	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProductgroupPK)) {
			return false;
		}
		ProductgroupPK castOther = (ProductgroupPK)other;
		return 
			this.productTypeCode.equals(castOther.productTypeCode)
			&& this.productGroupCode.equals(castOther.productGroupCode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.productTypeCode.hashCode();
		hash = hash * prime + this.productGroupCode.hashCode();
		
		return hash;
	}
}