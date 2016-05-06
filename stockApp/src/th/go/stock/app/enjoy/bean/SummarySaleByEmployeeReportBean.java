package th.go.stock.app.enjoy.bean;

public class SummarySaleByEmployeeReportBean {
	
	private String invoiceCode;
	private String invoiceDate;
	private String invoiceDateFrom;
	private String invoiceDateTo;
	private String cusName;
	private String invoiceTotal;
	private String saleCommission;
	private String tin;
	private String saleName;
	
	public SummarySaleByEmployeeReportBean(){
		this.invoiceCode 		= "";
		this.invoiceDate 		= "";
		this.invoiceDateFrom 	= "";
		this.invoiceDateTo 		= "";
		this.cusName 			= "";
		this.invoiceTotal 		= "0.00";
		this.saleCommission		= "0.00";
		this.tin 				= "";
		this.saleName			= "";
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
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

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getInvoiceTotal() {
		return invoiceTotal;
	}

	public void setInvoiceTotal(String invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}

	public String getSaleCommission() {
		return saleCommission;
	}

	public void setSaleCommission(String saleCommission) {
		this.saleCommission = saleCommission;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
}
