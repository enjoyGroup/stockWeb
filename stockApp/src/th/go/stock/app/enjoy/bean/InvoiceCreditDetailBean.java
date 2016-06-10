package th.go.stock.app.enjoy.bean;

public class InvoiceCreditDetailBean {
	
	private String invoiceCode;
	private String seqDb;
	private String productCode;
	private String productName;
	private String inventory;
	private String quantity;
	private String pricePerUnit;
	private String discount;
	private String price;
	private String seq;
	private String rowStatus;
	private String unitCode;
	private String unitName;
	private String quanDiscount;
	private String tin;
	
	public InvoiceCreditDetailBean(){
		this.invoiceCode 	= "";
		this.seqDb 			= "";
		this.productCode 	= "";
		this.productName	= "";
		this.inventory		= "0.00";
		this.quantity		= "0.00";
		this.pricePerUnit	= "";
		this.discount		= "0.00";
		this.price			= "0.00";
		this.seq 			= "";
		this.rowStatus		= "";
		this.unitCode		= "";
		this.unitName		= "";
		this.quanDiscount	= "0.00";
		this.tin			= "";
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getSeqDb() {
		return seqDb;
	}

	public void setSeqDb(String seqDb) {
		this.seqDb = seqDb;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(String pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getRowStatus() {
		return rowStatus;
	}

	public void setRowStatus(String rowStatus) {
		this.rowStatus = rowStatus;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getInventory() {
		return inventory;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getQuanDiscount() {
		return quanDiscount;
	}

	public void setQuanDiscount(String quanDiscount) {
		this.quanDiscount = quanDiscount;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}
}
