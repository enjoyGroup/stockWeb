package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the customer database table.
 * 
 */
@Embeddable
public class CustomerPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String cusCode;

	private String tin;

	public CustomerPK() {
	}
	public String getCusCode() {
		return this.cusCode;
	}
	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
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
		if (!(other instanceof CustomerPK)) {
			return false;
		}
		CustomerPK castOther = (CustomerPK)other;
		return 
			this.cusCode.equals(castOther.cusCode)
			&& this.tin.equals(castOther.tin);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cusCode.hashCode();
		hash = hash * prime + this.tin.hashCode();
		
		return hash;
	}
}