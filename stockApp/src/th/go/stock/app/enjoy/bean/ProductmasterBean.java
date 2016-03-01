package th.go.stock.app.enjoy.bean;



public class ProductmasterBean {
	
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
	private String salePrice1;
	private String salePrice2;
	private String salePrice3;
	private String salePrice4;
	private String salePrice5;
//	private String startDate;
//	private String expDate;
//	private String quantity;
//	private String productStatus;
	/*Begin สำหรับหน้า Search*/
	private String chkBox;
	private String chkBoxSeq;
	/*End สำหรับหน้า Search*/
	
	public ProductmasterBean(){
		this.productCode 		= "";
		this.productName 		= "";
		this.productTypeCode 	= "";
		this.productTypeName 	= "";
		this.productGroupCode 	= "";
		this.productGroupName 	= "";
		this.unitCode 			= "";
		this.unitName 			= "";
		this.minQuan 			= "0.00";
		this.costPrice 			= "0.00";
		this.salePrice1 		= "0.00";
		this.salePrice2 		= "0.00";
		this.salePrice3 		= "0.00";
		this.salePrice4 		= "0.00";
		this.salePrice5 		= "0.00";
//		this.startDate 			= "";
//		this.expDate 			= "";
//		this.quantity 			= "0";
//		this.productStatus 		= "";
		this.chkBox 			= "N";
		this.chkBoxSeq 			= "0";
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getSalePrice1() {
		return salePrice1;
	}

	public void setSalePrice1(String salePrice1) {
		this.salePrice1 = salePrice1;
	}

	public String getSalePrice2() {
		return salePrice2;
	}

	public void setSalePrice2(String salePrice2) {
		this.salePrice2 = salePrice2;
	}

	public String getSalePrice3() {
		return salePrice3;
	}

	public void setSalePrice3(String salePrice3) {
		this.salePrice3 = salePrice3;
	}

	public String getSalePrice4() {
		return salePrice4;
	}

	public void setSalePrice4(String salePrice4) {
		this.salePrice4 = salePrice4;
	}

	public String getSalePrice5() {
		return salePrice5;
	}

	public void setSalePrice5(String salePrice5) {
		this.salePrice5 = salePrice5;
	}

//	public String getQuantity() {
//		return quantity;
//	}
//
//	public void setQuantity(String quantity) {
//		this.quantity = quantity;
//	}

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
