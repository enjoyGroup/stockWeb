package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the refcustomerstatus database table.
 * 
 */
@Entity
@NamedQuery(name="Refcustomerstatus.findAll", query="SELECT r FROM Refcustomerstatus r")
public class Refcustomerstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String customerStatusCode;

	private String customerStatusName;

	public Refcustomerstatus() {
	}

	public String getCustomerStatusCode() {
		return this.customerStatusCode;
	}

	public void setCustomerStatusCode(String customerStatusCode) {
		this.customerStatusCode = customerStatusCode;
	}

	public String getCustomerStatusName() {
		return this.customerStatusName;
	}

	public void setCustomerStatusName(String customerStatusName) {
		this.customerStatusName = customerStatusName;
	}

}