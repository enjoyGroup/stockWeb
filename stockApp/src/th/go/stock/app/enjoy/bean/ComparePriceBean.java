package th.go.stock.app.enjoy.bean;

public class ComparePriceBean {
	
	private String productCode;
	private String productName;
	private String vendorCode;
	private String vendorName;
	private String branchName;
	private String seq;
	private String seqDb;
	private String quantity;
	private String price;
	private String rowStatus;
	private String tin;
	
	public ComparePriceBean(){
		this.productCode 		= "";
		this.productName 		= "";
		this.vendorCode 		= "";
		this.vendorName 		= "";
		this.branchName 		= "";
		this.seq 				= "";
		this.seqDb				= "";
		this.quantity 			= "0.00";
		this.price 				= "0.00";
		this.rowStatus			= "";
		this.tin				= "";
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getSeqDb() {
		return seqDb;
	}

	public void setSeqDb(String seqDb) {
		this.seqDb = seqDb;
	}

	public String getRowStatus() {
		return rowStatus;
	}

	public void setRowStatus(String rowStatus) {
		this.rowStatus = rowStatus;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}
}
