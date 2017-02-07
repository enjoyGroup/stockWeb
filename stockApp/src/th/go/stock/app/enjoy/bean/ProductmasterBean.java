package th.go.stock.app.enjoy.bean;

import org.apache.poi.ss.usermodel.Row;

import th.go.stock.app.enjoy.utils.ExcelField;



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
	/*Begin สำหรับหน้า Search*/
	private String chkBox;
	private String chkBoxSeq;
	/*End สำหรับหน้า Search*/
	private String tin;
	private String rowStatus;
	private String seq;
	private String quantity;
	private ExcelField colA;
	private ExcelField colB;
	private ExcelField colC;
	private ExcelField colD;
	private ExcelField colE;
	private ExcelField colF;
	private ExcelField colG;
	private ExcelField colH;
	private ExcelField colI;
	private ExcelField colJ;
	private String seqDis;
	
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
		this.chkBox 			= "N";
		this.chkBoxSeq 			= "0";
		this.tin				= "";
		this.rowStatus			= "";
		this.seq				= "0";
		this.quantity			= "0.00";
		this.seqDis				= "1";
	}
	
	public ProductmasterBean(Row row){
		this.colA			= new ExcelField(row, ".*", 0);
		this.colB			= new ExcelField(row, ".*", 1);
		this.colC			= new ExcelField(row, ".*", 2);
		this.colD			= new ExcelField(row, ".*", 3);
		this.colE			= new ExcelField(row, ".*", 4);
		this.colF			= new ExcelField(row, ".*", 5);
		this.colG			= new ExcelField(row, ".*", 6);
		this.colH			= new ExcelField(row, ".*", 7);
		this.colI			= new ExcelField(row, ".*", 8);
		this.colJ			= new ExcelField(row, ".*", 9);
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

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
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

	public ExcelField getColC() {
		return colC;
	}

	public void setColC(ExcelField colC) {
		this.colC = colC;
	}

	public ExcelField getColD() {
		return colD;
	}

	public void setColD(ExcelField colD) {
		this.colD = colD;
	}

	public ExcelField getColE() {
		return colE;
	}

	public void setColE(ExcelField colE) {
		this.colE = colE;
	}

	public ExcelField getColF() {
		return colF;
	}

	public void setColF(ExcelField colF) {
		this.colF = colF;
	}

	public ExcelField getColG() {
		return colG;
	}

	public void setColG(ExcelField colG) {
		this.colG = colG;
	}

	public ExcelField getColH() {
		return colH;
	}

	public void setColH(ExcelField colH) {
		this.colH = colH;
	}

	public ExcelField getColI() {
		return colI;
	}

	public void setColI(ExcelField colI) {
		this.colI = colI;
	}

	public ExcelField getColJ() {
		return colJ;
	}

	public void setColJ(ExcelField colJ) {
		this.colJ = colJ;
	}

	public String getSeqDis() {
		return seqDis;
	}

	public void setSeqDis(String seqDis) {
		this.seqDis = seqDis;
	}
}
