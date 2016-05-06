package th.go.stock.app.enjoy.bean;

public class HistoryPurchasedByDealerReportBean {
	
	private String tin;
	private String companyName;
	private String vendorCode;
	private String vendorName;
	private String branchName;
	private String reciveDate;
	private String reciveDateFrom;
	private String reciveDateTo;
	private String reciveTotal;
	private String reciveDiscount;
	private String reciveNo;
	
	public HistoryPurchasedByDealerReportBean(){
		this.tin 				= "";
		this.companyName 		= "";
		this.vendorCode 		= "";
		this.vendorName 		= "";
		this.branchName 		= "";
		this.reciveDate 		= "";
		this.reciveDateFrom 	= "";
		this.reciveDateTo 		= "";
		this.reciveTotal		= "0.00";
		this.reciveDiscount 	= "0.00";
		this.reciveNo			= "";
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getReciveDate() {
		return reciveDate;
	}

	public void setReciveDate(String reciveDate) {
		this.reciveDate = reciveDate;
	}

	public String getReciveDateFrom() {
		return reciveDateFrom;
	}

	public void setReciveDateFrom(String reciveDateFrom) {
		this.reciveDateFrom = reciveDateFrom;
	}

	public String getReciveDateTo() {
		return reciveDateTo;
	}

	public void setReciveDateTo(String reciveDateTo) {
		this.reciveDateTo = reciveDateTo;
	}

	public String getReciveTotal() {
		return reciveTotal;
	}

	public void setReciveTotal(String reciveTotal) {
		this.reciveTotal = reciveTotal;
	}

	public String getReciveDiscount() {
		return reciveDiscount;
	}

	public void setReciveDiscount(String reciveDiscount) {
		this.reciveDiscount = reciveDiscount;
	}

	public String getReciveNo() {
		return reciveNo;
	}

	public void setReciveNo(String reciveNo) {
		this.reciveNo = reciveNo;
	}
}
