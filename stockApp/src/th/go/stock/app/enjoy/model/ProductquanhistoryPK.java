package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the productquanhistory database table.
 * 
 */
@Embeddable
public class ProductquanhistoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int hisCode;

	private String tin;

	public ProductquanhistoryPK() {
	}
	public int getHisCode() {
		return this.hisCode;
	}
	public void setHisCode(int hisCode) {
		this.hisCode = hisCode;
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
		if (!(other instanceof ProductquanhistoryPK)) {
			return false;
		}
		ProductquanhistoryPK castOther = (ProductquanhistoryPK)other;
		return 
			(this.hisCode == castOther.hisCode)
			&& this.tin.equals(castOther.tin);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.hisCode;
		hash = hash * prime + this.tin.hashCode();
		
		return hash;
	}
}