package th.go.stock.app.enjoy.bean;


public class ProductQuanHistoryBean {
	
	private String hisCode;
	private String formRef;
	private String hisDate;
	private String hisDateFrom;
	private String hisDateTo;
	private String productType;
	private String productGroup;
	private String productCode;
	private String tin;
	private String quantityPlus;
	private String quantityMinus;
	private String quantityTotal;
	private String productName;
	private String productTypeName;
	private String productGroupName;
	
	public ProductQuanHistoryBean(){
		
		this.hisCode 			= "";
		this.formRef 			= "";
		this.hisDate 			= "";
		this.hisDateFrom 		= "";
		this.hisDateTo 			= "";
		this.productType 		= "";
		this.productGroup 		= "";
		this.productCode 		= "";
		this.tin 				= "";
		this.quantityPlus 		= "0.00";
		this.quantityMinus 		= "0.00";
		this.quantityTotal 		= "0.00";
		this.productName 		= "";
		this.productTypeName 	= "";
		this.productGroupName 	= "";
		
	}

	public String getHisCode() {
		return hisCode;
	}

	public void setHisCode(String hisCode) {
		this.hisCode = hisCode;
	}

	public String getFormRef() {
		return formRef;
	}

	public void setFormRef(String formRef) {
		this.formRef = formRef;
	}

	public String getHisDate() {
		return hisDate;
	}

	public void setHisDate(String hisDate) {
		this.hisDate = hisDate;
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

	public String getQuantityPlus() {
		return quantityPlus;
	}

	public void setQuantityPlus(String quantityPlus) {
		this.quantityPlus = quantityPlus;
	}

	public String getQuantityMinus() {
		return quantityMinus;
	}

	public void setQuantityMinus(String quantityMinus) {
		this.quantityMinus = quantityMinus;
	}

	public String getQuantityTotal() {
		return quantityTotal;
	}

	public void setQuantityTotal(String quantityTotal) {
		this.quantityTotal = quantityTotal;
	}

	public String getHisDateFrom() {
		return hisDateFrom;
	}

	public void setHisDateFrom(String hisDateFrom) {
		this.hisDateFrom = hisDateFrom;
	}

	public String getHisDateTo() {
		return hisDateTo;
	}

	public void setHisDateTo(String hisDateTo) {
		this.hisDateTo = hisDateTo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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
}
