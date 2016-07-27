package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the unittype database table.
 * 
 */
@Entity
@NamedQuery(name="Unittype.findAll", query="SELECT u FROM Unittype u")
public class Unittype implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UnittypePK id;

	private String unitName;

	private String unitStatus;

	public Unittype() {
	}

	public UnittypePK getId() {
		return this.id;
	}

	public void setId(UnittypePK id) {
		this.id = id;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitStatus() {
		return this.unitStatus;
	}

	public void setUnitStatus(String unitStatus) {
		this.unitStatus = unitStatus;
	}

}