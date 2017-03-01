package th.go.stock.app.enjoy.bean;

import org.apache.poi.ss.usermodel.Row;

import th.go.stock.app.enjoy.utils.ExcelField;



public class ManageProductGroupBean {
	
	private String productTypeCode;
	private String productGroupCode;
	private String productGroupCodeDis;
	private String productGroupName;
	private String productGroupStatus;
	private String rowStatus;
	private String seq;
	private String tin;
	private ExcelField colA;
	private ExcelField colB;
	
	public ManageProductGroupBean(){
		this.productTypeCode 		= "";
		this.productGroupCode 		= "";
		this.productGroupCodeDis	= "";
		this.productGroupName 		= "";
		this.productGroupStatus 	= "";
		this.rowStatus 				= "";
		this.seq 					= "";
		this.tin					= "";
	}
	
	public ManageProductGroupBean(Row row){
		this.colA			= new ExcelField(row, ".*", 0);
		this.colB			= new ExcelField(row, ".*", 1);
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
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

	public String getProductGroupStatus() {
		return productGroupStatus;
	}

	public void setProductGroupStatus(String productGroupStatus) {
		this.productGroupStatus = productGroupStatus;
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

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public ExcelField getColA() {
		return colA;
	}

	public void setColA(ExcelField colA) {
		this.colA = colA;
	}

	public ExcelField getColB() {
		return colB;
	}

	public void setColB(ExcelField colB) {
		this.colB = colB;
	}

	public String getProductGroupCodeDis() {
		return productGroupCodeDis;
	}

	public void setProductGroupCodeDis(String productGroupCodeDis) {
		this.productGroupCodeDis = productGroupCodeDis;
	}
}
