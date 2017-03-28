package th.go.stock.app.enjoy.bean;



public class ProductdetailBean {
	
	private String productCode;
	private String quanDiscount;
	private String discountRate;
	private String seqDb;
	private String rowStatus;
	private String seq;
	private String startDate;
	private String expDate;
	private String tin;
	private String availPageFlag;
	
	public ProductdetailBean(){
		this.productCode 	= "";
		this.quanDiscount 	= "";
		this.discountRate 	= "";
		this.seqDb 			= "";
		this.rowStatus 		= "";
		this.seq 			= "";
		this.startDate 		= "";
		this.expDate 		= "";
		this.tin			= "";
		this.availPageFlag	= "";
	}


	public String getProductCode() {
		return productCode;
	}


	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


	public String getQuanDiscount() {
		return quanDiscount;
	}


	public void setQuanDiscount(String quanDiscount) {
		this.quanDiscount = quanDiscount;
	}


	public String getDiscountRate() {
		return discountRate;
	}


	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
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


	public String getSeq() {
		return seq;
	}


	public void setSeq(String seq) {
		this.seq = seq;
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


	public String getTin() {
		return tin;
	}


	public void setTin(String tin) {
		this.tin = tin;
	}


	public String getAvailPageFlag() {
		return availPageFlag;
	}


	public void setAvailPageFlag(String availPageFlag) {
		this.availPageFlag = availPageFlag;
	}

}
