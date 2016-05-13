package th.go.stock.app.enjoy.bean;

public class AdjustStockBean {
	
	private String adjustNo;
	private String adjustDate;
	private String productCode;
	private String quanOld;
	private String quanNew;
	private String remark;
	private String productName;
	private String quantity;
	private String balanceVolume;
	private String unitName;
	private int lastOrder;
	private String tin;
	
	public AdjustStockBean(){
		this.adjustNo 			= "";
		this.adjustDate 		= "";
		this.productCode 		= "";
		this.quanOld 			= "0.00";
		this.quanNew 			= "0.00";
		this.remark 			= "";
		this.productName 		= "";
		this.quantity 			= "0.00";
		this.balanceVolume 		= "0.00";
		this.unitName			= "";
		this.lastOrder			= 0;
		this.tin				= "";
	}

	public String getAdjustNo() {
		return adjustNo;
	}

	public void setAdjustNo(String adjustNo) {
		this.adjustNo = adjustNo;
	}

	public String getAdjustDate() {
		return adjustDate;
	}

	public void setAdjustDate(String adjustDate) {
		this.adjustDate = adjustDate;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getQuanOld() {
		return quanOld;
	}

	public void setQuanOld(String quanOld) {
		this.quanOld = quanOld;
	}

	public String getQuanNew() {
		return quanNew;
	}

	public void setQuanNew(String quanNew) {
		this.quanNew = quanNew;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getBalanceVolume() {
		return balanceVolume;
	}

	public void setBalanceVolume(String balanceVolume) {
		this.balanceVolume = balanceVolume;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public int getLastOrder() {
		return lastOrder;
	}

	public void setLastOrder(int lastOrder) {
		this.lastOrder = lastOrder;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

}
