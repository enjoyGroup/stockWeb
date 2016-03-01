package th.go.stock.app.enjoy.bean;

public class InvoiceCashMasterBean {
	
	private String invoiceCode;
	private String invoiceDate;
	private String invoiceDateForm;
	private String invoiceDateTo;
	private String invoiceType;
	private String cusCode;
	private String branchName;
	private String saleUniqueId;
	private String saleName;
	private String saleCommission;
	private String invoicePrice;
	private String invoicediscount;
	private String invoiceDeposit;
	private String invoiceVat;
	private String invoiceTotal;
	private String userUniqueId;
	private String invoiceCredit;
	private String invoiceStatus;
	private String invoiceStatusDesc;
	private String cusFullName;
	private String invoiceTypeDesc;
	private String tin;

	
	public InvoiceCashMasterBean(){
		this.invoiceCode 		= "";
		this.invoiceDate 		= "";
		this.invoiceDateForm 	= "";
		this.invoiceDateTo 		= "";
		this.invoiceType 		= "";
		this.cusCode 			= "";
		this.branchName 		= "";
		this.saleUniqueId 		= "";
		this.saleName			= "";
		this.saleCommission 	= "0.00";
		this.invoicePrice 		= "0.00";
		this.invoicediscount 	= "0.00";
		this.invoiceDeposit 	= "0.00";
		this.invoiceVat 		= "0.00";
		this.invoiceTotal 		= "0.00";
		this.userUniqueId 		= "";
		this.invoiceCredit 		= "0.00";
		this.invoiceStatus 		= "";
		this.invoiceStatusDesc	= "";
		this.cusFullName		= "";
		this.invoiceTypeDesc	= "";
		this.tin				= "";
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

	public String getInvoiceDateForm() {
		return invoiceDateForm;
	}

	public void setInvoiceDateForm(String invoiceDateForm) {
		this.invoiceDateForm = invoiceDateForm;
	}

	public String getInvoiceDateTo() {
		return invoiceDateTo;
	}

	public void setInvoiceDateTo(String invoiceDateTo) {
		this.invoiceDateTo = invoiceDateTo;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getCusCode() {
		return cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getSaleUniqueId() {
		return saleUniqueId;
	}

	public void setSaleUniqueId(String saleUniqueId) {
		this.saleUniqueId = saleUniqueId;
	}

	public String getSaleCommission() {
		return saleCommission;
	}

	public void setSaleCommission(String saleCommission) {
		this.saleCommission = saleCommission;
	}

	public String getInvoicePrice() {
		return invoicePrice;
	}

	public void setInvoicePrice(String invoicePrice) {
		this.invoicePrice = invoicePrice;
	}

	public String getInvoicediscount() {
		return invoicediscount;
	}

	public void setInvoicediscount(String invoicediscount) {
		this.invoicediscount = invoicediscount;
	}

	public String getInvoiceDeposit() {
		return invoiceDeposit;
	}

	public void setInvoiceDeposit(String invoiceDeposit) {
		this.invoiceDeposit = invoiceDeposit;
	}

	public String getInvoiceVat() {
		return invoiceVat;
	}

	public void setInvoiceVat(String invoiceVat) {
		this.invoiceVat = invoiceVat;
	}

	public String getInvoiceTotal() {
		return invoiceTotal;
	}

	public void setInvoiceTotal(String invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}

	public String getUserUniqueId() {
		return userUniqueId;
	}

	public void setUserUniqueId(String userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

	public String getInvoiceCredit() {
		return invoiceCredit;
	}

	public void setInvoiceCredit(String invoiceCredit) {
		this.invoiceCredit = invoiceCredit;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getCusFullName() {
		return cusFullName;
	}

	public void setCusFullName(String cusFullName) {
		this.cusFullName = cusFullName;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public String getInvoiceTypeDesc() {
		return invoiceTypeDesc;
	}

	public void setInvoiceTypeDesc(String invoiceTypeDesc) {
		this.invoiceTypeDesc = invoiceTypeDesc;
	}

	public String getInvoiceStatusDesc() {
		return invoiceStatusDesc;
	}

	public void setInvoiceStatusDesc(String invoiceStatusDesc) {
		this.invoiceStatusDesc = invoiceStatusDesc;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}
}
