package th.go.stock.app.enjoy.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the unittype database table.
 * 
 */
@Entity
@NamedQuery(name="Unittype.findAll", query="SELECT u FROM Unittype u")
public class Unittype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int unitCode;

	private String unitName;

	private String unitStatus;

	public Unittype() {
	}

	public int getUnitCode() {
		return this.unitCode;
	}

	public void setUnitCode(int unitCode) {
		this.unitCode = unitCode;
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