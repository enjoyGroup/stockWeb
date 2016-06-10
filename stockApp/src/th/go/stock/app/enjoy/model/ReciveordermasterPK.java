package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the reciveordermaster database table.
 * 
 */
@Embeddable
public class ReciveordermasterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String reciveNo;

	private String tin;

	public ReciveordermasterPK() {
	}
	public String getReciveNo() {
		return this.reciveNo;
	}
	public void setReciveNo(String reciveNo) {
		this.reciveNo = reciveNo;
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
		if (!(other instanceof ReciveordermasterPK)) {
			return false;
		}
		ReciveordermasterPK castOther = (ReciveordermasterPK)other;
		return 
			this.reciveNo.equals(castOther.reciveNo)
			&& this.tin.equals(castOther.tin);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.reciveNo.hashCode();
		hash = hash * prime + this.tin.hashCode();
		
		return hash;
	}
}