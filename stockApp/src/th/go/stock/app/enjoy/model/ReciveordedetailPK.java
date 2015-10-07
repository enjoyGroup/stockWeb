package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the reciveordedetail database table.
 * 
 */
@Embeddable
public class ReciveordedetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String reciveNo;

	private int seq;

	public ReciveordedetailPK() {
	}
	public String getReciveNo() {
		return this.reciveNo;
	}
	public void setReciveNo(String reciveNo) {
		this.reciveNo = reciveNo;
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
		if (!(other instanceof ReciveordedetailPK)) {
			return false;
		}
		ReciveordedetailPK castOther = (ReciveordedetailPK)other;
		return 
			this.reciveNo.equals(castOther.reciveNo)
			&& (this.seq == castOther.seq);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.reciveNo.hashCode();
		hash = hash * prime + this.seq;
		
		return hash;
	}
}