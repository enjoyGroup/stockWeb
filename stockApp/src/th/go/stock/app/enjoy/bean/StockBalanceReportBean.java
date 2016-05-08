package th.go.stock.app.enjoy.bean;

public class StockBalanceReportBean {
	
	private String productTypeName;
	private String productGroupName;
	private String productName;
	private String quantity;
	private String productType;
	private String productGroup;
	private String tin;
	
	public StockBalanceReportBean(){
		this.productTypeName 	= "";
		this.productGroupName 	= "";
		this.productName 		= "";
		this.quantity 			= "0.00";
		this.productType 		= "";
		this.productGroup 		= "";
		this.tin 				= "";
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}
}
