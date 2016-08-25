package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the unittype database table.
 * 
 */
@Embeddable
public class UnittypePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int unitCode;

	private String tin;

	public UnittypePK() {
	}
	public int getUnitCode() {
		return this.unitCode;
	}
	public void setUnitCode(int unitCode) {
		this.unitCode = unitCode;
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
		if (!(other instanceof UnittypePK)) {
			return false;
		}
		UnittypePK castOther = (UnittypePK)other;
		return 
			(this.unitCode == castOther.unitCode)
			&& this.tin.equals(castOther.tin);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.unitCode;
		hash = hash * prime + this.tin.hashCode();
		
		return hash;
	}
}