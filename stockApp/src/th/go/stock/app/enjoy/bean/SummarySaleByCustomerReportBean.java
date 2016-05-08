package th.go.stock.app.enjoy.bean;

public class SummarySaleByCustomerReportBean {
	
	private String productName;
	private String invoiceDate;
	private String invoiceDateFrom;
	private String invoiceDateTo;
	private String quantity;
	private String price;
	private String discount;
	private String cusName;
	private String cusCode;
	
	public SummarySaleByCustomerReportBean(){
		this.productName 		= "";
		this.invoiceDate 		= "";
		this.invoiceDateFrom 	= "";
		this.invoiceDateTo 		= "";
		this.quantity 			= "0.00";
		this.price				= "0.00";
		this.discount 			= "0.00";
		this.cusName 			= "";
		this.cusCode			= "";
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusCode() {
		return cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}
}
