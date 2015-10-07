package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the refgeneralstatus database table.
 * 
 */
@Entity
@NamedQuery(name="Refgeneralstatus.findAll", query="SELECT r FROM Refgeneralstatus r")
public class Refgeneralstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String generalStatusCode;

	private String generalStatusName;

	public Refgeneralstatus() {
	}

	public String getGeneralStatusCode() {
		return this.generalStatusCode;
	}

	public void setGeneralStatusCode(String generalStatusCode) {
		this.generalStatusCode = generalStatusCode;
	}

	public String getGeneralStatusName() {
		return this.generalStatusName;
	}

	public void setGeneralStatusName(String generalStatusName) {
		this.generalStatusName = generalStatusName;
	}

}