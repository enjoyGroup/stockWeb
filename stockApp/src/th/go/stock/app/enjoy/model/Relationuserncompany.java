package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the relationuserncompany database table.
 * 
 */
@Entity
@NamedQuery(name="Relationuserncompany.findAll", query="SELECT r FROM Relationuserncompany r")
public class Relationuserncompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int userUniqueId;

	private String tin;

	public Relationuserncompany() {
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

}