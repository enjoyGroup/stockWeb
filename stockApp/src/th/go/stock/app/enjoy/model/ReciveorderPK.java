package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the reciveorder database table.
 * 
 */
@Embeddable
public class ReciveorderPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String reciveNo;

	private String productCode;

	public ReciveorderPK() {
	}
	public String getReciveNo() {
		return this.reciveNo;
	}
	public void setReciveNo(String reciveNo) {
		this.reciveNo = reciveNo;
	}
	public String getProductCode() {
		return this.productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ReciveorderPK)) {
			return false;
		}
		ReciveorderPK castOther = (ReciveorderPK)other;
		return 
			this.reciveNo.equals(castOther.reciveNo)
			&& this.productCode.equals(castOther.productCode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.reciveNo.hashCode();
		hash = hash * prime + this.productCode.hashCode();
		
		return hash;
	}
}