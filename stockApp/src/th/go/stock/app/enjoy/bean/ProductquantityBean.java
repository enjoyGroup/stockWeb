package th.go.stock.app.enjoy.bean;


public class ProductquantityBean {
	private String productCode;
	private String tin;
	private String quantity;
	
	public ProductquantityBean(){
		this.productCode 				= "";
		this.tin						= "";
		this.quantity 					= "";
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}
