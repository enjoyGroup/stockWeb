package th.go.stock.app.enjoy.bean;

public class ReciveStockBean {
	
	private String reciveNo;
	private String productCode;
	private String reciveDate;
	private String userUniqueId;
	private String reciveStatusCode;
	private String reciveStatusName;
	private String rowStatus;
	private String seq;
	
	public ReciveStockBean(){
		this.reciveNo 			= "";
		this.productCode 		= "";
		this.reciveDate 		= "";
		this.userUniqueId 		= "";
		this.reciveStatusCode 	= "";
		this.reciveStatusName 	= "";
		this.rowStatus 			= "";
		this.seq 				= "";
	}

	public String getReciveNo() {
		return reciveNo;
	}

	public void setReciveNo(String reciveNo) {
		this.reciveNo = reciveNo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getReciveDate() {
		return reciveDate;
	}

	public void setReciveDate(String reciveDate) {
		this.reciveDate = reciveDate;
	}

	public String getUserUniqueId() {
		return userUniqueId;
	}

	public void setUserUniqueId(String userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

	public String getReciveStatusCode() {
		return reciveStatusCode;
	}

	public void setReciveStatusCode(String reciveStatusCode) {
		this.reciveStatusCode = reciveStatusCode;
	}

	public String getReciveStatusName() {
		return reciveStatusName;
	}

	public void setReciveStatusName(String reciveStatusName) {
		this.reciveStatusName = reciveStatusName;
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
