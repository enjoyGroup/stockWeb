package th.go.stock.app.enjoy.bean;



public class ProductDetailsBean {
	
	private String productCode;
	private String productName;
	private String productTypeCode;
	private String productTypeName;
	private String productGroupCode;
	private String productGroupName;
	private String unitCode;
	private String unitName;
	private String minQuan;
	private String costPrice;
	private String salePrice;
	private String startDate;
	private String expDate;
	private String quantity;
	private String productStatus;
	/*Begin สำหรับหน้า Search*/
	private String chkBox;
	private String chkBoxSeq;
	/*End สำหรับหน้า Search*/
	
	public ProductDetailsBean(){
		this.productCode 		= "";
		this.productName 		= "";
		this.productTypeCode 	= "";
		this.productTypeName 	= "";
		this.productGroupCode 	= "";
		this.productGroupName 	= "";
		this.unitCode 			= "";
		this.unitName 			= "";
		this.minQuan 			= "";
		this.costPrice 			= "";
		this.salePrice 			= "";
		this.startDate 			= "";
		this.expDate 			= "";
		this.quantity 			= "0";
		this.productStatus 		= "";
		this.chkBox 			= "N";
		this.chkBoxSeq 			= "0";
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getProductGroupCode() {
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
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

	public String getMinQuan() {
		return minQuan;
	}

	public void setMinQuan(String minQuan) {
		this.minQuan = minQuan;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getChkBox() {
		return chkBox;
	}

	public void setChkBox(String chkBox) {
		this.chkBox = chkBox;
	}

	public String getChkBoxSeq() {
		return chkBoxSeq;
	}

	public void setChkBoxSeq(String chkBoxSeq) {
		this.chkBoxSeq = chkBoxSeq;
	}
}
