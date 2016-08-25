package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the companyvendor database table.
 * 
 */
@Embeddable
public class CompanyvendorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int vendorCode;

	private String tinCompany;

	public CompanyvendorPK() {
	}
	public int getVendorCode() {
		return this.vendorCode;
	}
	public void setVendorCode(int vendorCode) {
		this.vendorCode = vendorCode;
	}
	public String getTinCompany() {
		return this.tinCompany;
	}
	public void setTinCompany(String tinCompany) {
		this.tinCompany = tinCompany;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CompanyvendorPK)) {
			return false;
		}
		CompanyvendorPK castOther = (CompanyvendorPK)other;
		return 
			(this.vendorCode == castOther.vendorCode)
			&& this.tinCompany.equals(castOther.tinCompany);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.vendorCode;
		hash = hash * prime + this.tinCompany.hashCode();
		
		return hash;
	}
}