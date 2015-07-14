package th.go.stock.app.enjoy.bean;



public class ManageProductGroupBean {
	
	private String productTypeCode;
	private String productGroupCode;
	private String productGroupName;
	private String productGroupStatus;
	private String rowStatus;
	private String seq;
	
	
	public ManageProductGroupBean(){
		this.productTypeCode 		= "";
		this.productGroupCode 		= "";
		this.productGroupName 		= "";
		this.productGroupStatus 	= "";
		this.rowStatus 				= "";
		this.seq 					= "";
	}


	public String getProductTypeCode() {
		return productTypeCode;
	}


	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}


	public String getProductGroupCode() {
		return productGroupCode;
	}


	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}


	public String getProductGroupName() {
		return productGroupName;
	}


	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}


	public String getProductGroupStatus() {
		return productGroupStatus;
	}


	public void setProductGroupStatus(String productGroupStatus) {
		this.productGroupStatus = productGroupStatus;
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
