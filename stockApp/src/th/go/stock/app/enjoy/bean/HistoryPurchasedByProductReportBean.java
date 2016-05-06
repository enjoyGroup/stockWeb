package th.go.stock.app.enjoy.bean;

public class HistoryPurchasedByProductReportBean {
	
	private String productName;
	private String vendorName;
	private String reciveNo;
	private String reciveDate;
	private String reciveDateFrom;
	private String reciveDateTo;
	private String costPrice;
	private String discountRate;
	private String tin;
	private String productCode;
	
	public HistoryPurchasedByProductReportBean(){
		this.productName 		= "";
		this.vendorName 		= "";
		this.reciveNo 			= "";
		this.reciveDate 		= "";
		this.reciveDateFrom 	= "";
		this.reciveDateTo 		= "";
		this.reciveDateFrom 	= "";
		this.costPrice 			= "0.00";
		this.discountRate		= "0.00";
		this.tin 				= "";
		this.productCode		= "";
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getReciveNo() {
		return reciveNo;
	}

	public void setReciveNo(String reciveNo) {
		this.reciveNo = reciveNo;
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

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}
