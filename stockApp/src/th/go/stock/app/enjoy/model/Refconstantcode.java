package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the refconstantcode database table.
 * 
 */
@Entity
@NamedQuery(name="Refconstantcode.findAll", query="SELECT r FROM Refconstantcode r")
public class Refconstantcode implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RefconstantcodePK id;

	private String codeDisplay;

	private String codeNameEN;

	private String codeNameTH;

	private String flagEdit;

	private String flagYear;

	private String typeTB;

	public Refconstantcode() {
	}

	public RefconstantcodePK getId() {
		return this.id;
	}

	public void setId(RefconstantcodePK id) {
		this.id = id;
	}

	public String getCodeDisplay() {
		return this.codeDisplay;
	}

	public void setCodeDisplay(String codeDisplay) {
		this.codeDisplay = codeDisplay;
	}

	public String getCodeNameEN() {
		return this.codeNameEN;
	}

	public void setCodeNameEN(String codeNameEN) {
		this.codeNameEN = codeNameEN;
	}

	public String getCodeNameTH() {
		return this.codeNameTH;
	}

	public void setCodeNameTH(String codeNameTH) {
		this.codeNameTH = codeNameTH;
	}

	public String getFlagEdit() {
		return this.flagEdit;
	}

	public void setFlagEdit(String flagEdit) {
		this.flagEdit = flagEdit;
	}

	public String getFlagYear() {
		return this.flagYear;
	}

	public void setFlagYear(String flagYear) {
		this.flagYear = flagYear;
	}

	public String getTypeTB() {
		return this.typeTB;
	}

	public void setTypeTB(String typeTB) {
		this.typeTB = typeTB;
	}

}