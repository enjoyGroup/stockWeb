package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the relationuserncompany database table.
 * 
 */
@Embeddable
public class RelationuserncompanyPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int userUniqueId;

	private String tin;

	public RelationuserncompanyPK() {
	}
	public int getUserUniqueId() {
		return this.userUniqueId;
	}
	public void setUserUniqueId(int userUniqueId) {
		this.userUniqueId = userUniqueId;
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
		if (!(other instanceof RelationuserncompanyPK)) {
			return false;
		}
		RelationuserncompanyPK castOther = (RelationuserncompanyPK)other;
		return 
			(this.userUniqueId == castOther.userUniqueId)
			&& this.tin.equals(castOther.tin);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userUniqueId;
		hash = hash * prime + this.tin.hashCode();
		
		return hash;
	}
}