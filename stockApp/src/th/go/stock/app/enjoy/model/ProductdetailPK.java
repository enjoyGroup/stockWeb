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

	private int productCode;

	private String tin;

	private int seq;

	public ProductdetailPK() {
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
			(this.productCode == castOther.productCode)
			&& this.tin.equals(castOther.tin)
			&& (this.seq == castOther.seq);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.productCode;
		hash = hash * prime + this.tin.hashCode();
		hash = hash * prime + this.seq;
		
		return hash;
	}
}
