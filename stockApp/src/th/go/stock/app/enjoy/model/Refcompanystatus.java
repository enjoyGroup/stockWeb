package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the refcompanystatus database table.
 * 
 */
@Entity
@NamedQuery(name="Refcompanystatus.findAll", query="SELECT r FROM Refcompanystatus r")
public class Refcompanystatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String companyStatusCode;

	private String companyStatusName;

	public Refcompanystatus() {
	}

	public String getCompanyStatusCode() {
		return this.companyStatusCode;
	}

	public void setCompanyStatusCode(String companyStatusCode) {
		this.companyStatusCode = companyStatusCode;
	}

	public String getCompanyStatusName() {
		return this.companyStatusName;
	}

	public void setCompanyStatusName(String companyStatusName) {
		this.companyStatusName = companyStatusName;
	}

}