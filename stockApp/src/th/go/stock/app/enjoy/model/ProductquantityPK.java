package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the productquantity database table.
 * 
 */
@Embeddable
public class ProductquantityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int productCode;

	private String tin;

	public ProductquantityPK() {
	}
	public int getProductCode() {
		return this.productCode;
	}
	public void setProductCode(int productCode) {
		this.productCode = productCode;
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
		if (!(other instanceof ProductquantityPK)) {
			return false;
		}
		ProductquantityPK castOther = (ProductquantityPK)other;
		return 
			(this.productCode == castOther.productCode)
			&& this.tin.equals(castOther.tin);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.productCode;
		hash = hash * prime + this.tin.hashCode();
		
		return hash;
	}
}
