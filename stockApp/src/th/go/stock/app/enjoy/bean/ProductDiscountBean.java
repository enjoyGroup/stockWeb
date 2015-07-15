package th.go.stock.app.enjoy.bean;



public class ProductDiscountBean {
	
	private String productCode;
	private String quanDiscount;
	private String discount;
	private String seqDb;
	private String rowStatus;
	private String seq;
	
	
	public ProductDiscountBean(){
		this.productCode 	= "";
		this.quanDiscount 	= "";
		this.discount 		= "";
		this.seqDb 			= "";
		this.rowStatus 		= "";
		this.seq 			= "";
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


	public String getDiscount() {
		return discount;
	}


	public void setDiscount(String discount) {
		this.discount = discount;
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
}
