package th.go.stock.app.enjoy.bean;



public class ManageProductTypeBean {
	
	private String productTypeCode;
	private String productTypeName;
	private String productTypeStatus;
	private String rowStatus;
	private String seq;
	
	
	public ManageProductTypeBean(){
		this.productTypeCode 		= "";
		this.productTypeName 		= "";
		this.productTypeStatus 		= "";
		this.rowStatus 				= "";
		this.seq 					= "";
	}


	public String getProductTypeCode() {
		return productTypeCode;
	}


	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}


	public String getProductTypeName() {
		return productTypeName;
	}


	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}


	public String getProductTypeStatus() {
		return productTypeStatus;
	}


	public void setProductTypeStatus(String productTypeStatus) {
		this.productTypeStatus = productTypeStatus;
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
