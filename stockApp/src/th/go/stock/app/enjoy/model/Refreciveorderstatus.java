package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the refreciveorderstatus database table.
 * 
 */
@Entity
@NamedQuery(name="Refreciveorderstatus.findAll", query="SELECT r FROM Refreciveorderstatus r")
public class Refreciveorderstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String reciveStatusCode;

	private String reciveStatusName;

	public Refreciveorderstatus() {
	}

	public String getReciveStatusCode() {
		return this.reciveStatusCode;
	}

	public void setReciveStatusCode(String reciveStatusCode) {
		this.reciveStatusCode = reciveStatusCode;
	}

	public String getReciveStatusName() {
		return this.reciveStatusName;
	}

	public void setReciveStatusName(String reciveStatusName) {
		this.reciveStatusName = reciveStatusName;
	}

}