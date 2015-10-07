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
	
	public AdjustStockBean(){
		this.adjustNo 			= "";
		this.adjustDate 		= "";
		this.productCode 		= "";
		this.quanOld 			= "";
		this.quanNew 			= "";
		this.remark 			= "";
		this.productName 		= "";
		this.quantity 			= "";
		this.balanceVolume 		= "";
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
}
