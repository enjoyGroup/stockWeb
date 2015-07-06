package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the userprivilege database table.
 * 
 */
@Entity
@NamedQuery(name="Userprivilege.findAll", query="SELECT u FROM Userprivilege u")
public class Userprivilege implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String privilegeCode;

	private String pagesCode;

	private String privilegeName;

	public Userprivilege() {
	}

	public String getPrivilegeCode() {
		return this.privilegeCode;
	}

	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	public String getPagesCode() {
		return this.pagesCode;
	}

	public void setPagesCode(String pagesCode) {
		this.pagesCode = pagesCode;
	}

	public String getPrivilegeName() {
		return this.privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

}