package th.go.stock.app.enjoy.bean;

public class SummarySaleByMonthReportBean {
	
	private String invoiceCode;
	private String cusName;
	private String productName;
	private String quantity;
	private String price;
	private String tin;
	private String invoiceDate;
	private String invoiceDateFrom;
	private String invoiceDateTo;
	private String invoiceMonth;
	
	public SummarySaleByMonthReportBean(){
		this.invoiceCode 		= "";
		this.cusName 			= "";
		this.productName 		= "";
		this.quantity 			= "0.00";
		this.price 				= "0.00";
		this.tin 				= "";
		this.invoiceDate 		= "";
		this.invoiceDateFrom 	= "";
		this.invoiceDateTo 		= "";
		this.invoiceMonth		= "";
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceDateFrom() {
		return invoiceDateFrom;
	}

	public void setInvoiceDateFrom(String invoiceDateFrom) {
		this.invoiceDateFrom = invoiceDateFrom;
	}

	public String getInvoiceDateTo() {
		return invoiceDateTo;
	}

	public void setInvoiceDateTo(String invoiceDateTo) {
		this.invoiceDateTo = invoiceDateTo;
	}

	public String getInvoiceMonth() {
		return invoiceMonth;
	}

	public void setInvoiceMonth(String invoiceMonth) {
		this.invoiceMonth = invoiceMonth;
	}
}
