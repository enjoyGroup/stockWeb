package th.go.stock.app.enjoy.bean;


public class RefconstantcodeBean {
	private String id;
	private String codeDisplay;
	private String codeNameTH;
	private String codeNameEN;
	private String rowStatus;
	
	public RefconstantcodeBean(){
		this.id 				= "";
		this.codeDisplay 		= "";
		this.codeNameTH			= "";
		this.codeNameEN 		= "";
		this.rowStatus			= "";
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
}
