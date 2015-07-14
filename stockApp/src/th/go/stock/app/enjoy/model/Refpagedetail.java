package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the refpagedetails database table.
 * 
 */
@Entity
@Table(name="refpagedetails")
@NamedQuery(name="Refpagedetail.findAll", query="SELECT r FROM Refpagedetail r")
public class Refpagedetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String pageCodes;

	private String pageNames;

	private String pathPages;

	public Refpagedetail() {
	}

	public String getPageCodes() {
		return this.pageCodes;
	}

	public void setPageCodes(String pageCodes) {
		this.pageCodes = pageCodes;
	}

	public String getPageNames() {
		return this.pageNames;
	}

	public void setPageNames(String pageNames) {
		this.pageNames = pageNames;
	}

	public String getPathPages() {
		return this.pathPages;
	}

	public void setPathPages(String pathPages) {
		this.pathPages = pathPages;
	}

}