package th.go.stock.app.enjoy.bean;


public class RefconstantcodeBean {
	private String id;
	private String codeDisplay;
	private String codeNameTH;
	private String codeNameEN;
	private String rowStatus;
	private String flagYear;
	private String flagEdit;
	
	public RefconstantcodeBean(){
		this.id 				= "";
		this.codeDisplay 		= "";
		this.codeNameTH			= "";
		this.codeNameEN 		= "";
		this.rowStatus			= "";
		this.flagYear			= "N";
		this.flagEdit			= "N";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodeDisplay() {
		return codeDisplay;
	}

	public void setCodeDisplay(String codeDisplay) {
		this.codeDisplay = codeDisplay;
	}

	public String getCodeNameTH() {
		return codeNameTH;
	}

	public void setCodeNameTH(String codeNameTH) {
		this.codeNameTH = codeNameTH;
	}

	public String getCodeNameEN() {
		return codeNameEN;
	}

	public void setCodeNameEN(String codeNameEN) {
		this.codeNameEN = codeNameEN;
	}

	public String getRowStatus() {
		return rowStatus;
	}

	public void setRowStatus(String rowStatus) {
		this.rowStatus = rowStatus;
	}

	public String getFlagYear() {
		return flagYear;
	}

	public void setFlagYear(String flagYear) {
		this.flagYear = flagYear;
	}

	public String getFlagEdit() {
		return flagEdit;
	}

	public void setFlagEdit(String flagEdit) {
		this.flagEdit = flagEdit;
	}
}
