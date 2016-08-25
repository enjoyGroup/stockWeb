package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the relationgroupcustomer database table.
 * 
 */
@Embeddable
public class RelationgroupcustomerPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int cusGroupCode;

	private String tin;

	public RelationgroupcustomerPK() {
	}
	public int getCusGroupCode() {
		return this.cusGroupCode;
	}
	public void setCusGroupCode(int cusGroupCode) {
		this.cusGroupCode = cusGroupCode;
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
		if (!(other instanceof RelationgroupcustomerPK)) {
			return false;
		}
		RelationgroupcustomerPK castOther = (RelationgroupcustomerPK)other;
		return 
			(this.cusGroupCode == castOther.cusGroupCode)
			&& this.tin.equals(castOther.tin);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cusGroupCode;
		hash = hash * prime + this.tin.hashCode();
		
		return hash;
	}
}