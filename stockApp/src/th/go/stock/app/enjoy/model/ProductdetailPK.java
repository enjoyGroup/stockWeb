package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the productdetail database table.
 * 
 */
@Embeddable
public class ProductdetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String productCode;

	private int seq;

	public ProductdetailPK() {
	}
	public String getProductCode() {
		return this.productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getSeq() {
		return this.seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProductdetailPK)) {
			return false;
		}
		ProductdetailPK castOther = (ProductdetailPK)other;
		return 
			this.productCode.equals(castOther.productCode)
			&& (this.seq == castOther.seq);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.productCode.hashCode();
		hash = hash * prime + this.seq;
		
		return hash;
	}
}