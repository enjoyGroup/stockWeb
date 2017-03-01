package th.go.stock.app.enjoy.bean;

import org.apache.poi.ss.usermodel.Row;

import th.go.stock.app.enjoy.utils.ExcelField;



public class ManageProductTypeBean {
	
	private String productTypeCode;
	private String productTypeName;
	private String productTypeStatus;
	private String rowStatus;
	private String seq;
	private String tin;
	private String productTypeCodeDis;
	private ExcelField colA;
	private ExcelField colB;
	
	
	public ManageProductTypeBean(){
		this.productTypeCode 		= "";
		this.productTypeName 		= "";
		this.productTypeStatus 		= "";
		this.rowStatus 				= "";
		this.seq 					= "";
		this.tin					= "";
		this.productTypeCodeDis		= "";
	}
	
	public ManageProductTypeBean(Row row){
		this.colA			= new ExcelField(row, ".*", 0);
		this.colB			= new ExcelField(row, ".*", 1);
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


	public String getProductTypeStatus() {
		return productTypeStatus;
	}


	public void setProductTypeStatus(String productTypeStatus) {
		this.productTypeStatus = productTypeStatus;
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

	public String getProductTypeCodeDis() {
		return productTypeCodeDis;
	}

	public void setProductTypeCodeDis(String productTypeCodeDis) {
		this.productTypeCodeDis = productTypeCodeDis;
	}
}
