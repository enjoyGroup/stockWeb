package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the refuserstatus database table.
 * 
 */
@Entity
@NamedQuery(name="Refuserstatus.findAll", query="SELECT r FROM Refuserstatus r")
public class Refuserstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String userStatusCode;

	private String userStatusName;

	public Refuserstatus() {
	}

	public String getUserStatusCode() {
		return this.userStatusCode;
	}

	public void setUserStatusCode(String userStatusCode) {
		this.userStatusCode = userStatusCode;
	}

	public String getUserStatusName() {
		return this.userStatusName;
	}

	public void setUserStatusName(String userStatusName) {
		this.userStatusName = userStatusName;
	}

}