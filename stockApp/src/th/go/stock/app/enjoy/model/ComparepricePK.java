package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the compareprice database table.
 * 
 */
@Embeddable
public class ComparepricePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String productCode;

	private int seq;

	public ComparepricePK() {
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
		if (!(other instanceof ComparepricePK)) {
			return false;
		}
		ComparepricePK castOther = (ComparepricePK)other;
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