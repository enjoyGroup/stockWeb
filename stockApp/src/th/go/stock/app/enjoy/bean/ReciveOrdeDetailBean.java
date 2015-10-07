package th.go.stock.app.enjoy.bean;

public class ReciveOrdeDetailBean {
	
	private String reciveNo;
	private String seqDb;
	private String productCode;
	private String productName;
	private String inventory;
	private String quantity;
	private String unitCode;
	private String unitName;
	private String price;
	private String discountRate;
	private String costPrice;
	private String seq;
	private String rowStatus;
	
	public ReciveOrdeDetailBean(){
		this.reciveNo 		= "";
		this.seqDb 			= "";
		this.productCode 	= "";
		this.productName	= "";
		this.inventory		= "";
		this.quantity 		= "0.00";
		this.unitCode 		= "";
		this.unitName 		= "";
		this.price 			= "0.00";
		this.discountRate 	= "0.00";
		this.costPrice 		= "0.00";
		this.seq 			= "";
		this.rowStatus		= "";
	}

	public String getReciveNo() {
		return reciveNo;
	}

	public void setReciveNo(String reciveNo) {
		this.reciveNo = reciveNo;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
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
}
