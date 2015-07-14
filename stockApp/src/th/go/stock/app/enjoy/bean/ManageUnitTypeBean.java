package th.go.stock.app.enjoy.bean;



public class ManageUnitTypeBean {
	
	private String unitCode;
	private String unitName;
	private String unitStatus;
	private String rowStatus;
	private String seq;
	
	
	public ManageUnitTypeBean(){
		this.unitCode 		= "";
		this.unitName 		= "";
		this.unitStatus 	= "";
		this.rowStatus 		= "";
		this.seq 			= "";
	}


	public String getUnitCode() {
		return unitCode;
	}


	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}


	public String getUnitName() {
		return unitName;
	}


	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}


	public String getUnitStatus() {
		return unitStatus;
	}


	public void setUnitStatus(String unitStatus) {
		this.unitStatus = unitStatus;
	}


	public String getRowStatus() {
		return rowStatus;
	}


	public void setRowStatus(String rowStatus) {
		this.rowStatus = rowStatus;
	}


	public String getSeq() {
		return seq;
	}


	public void setSeq(String seq) {
		this.seq = seq;
	}
}
