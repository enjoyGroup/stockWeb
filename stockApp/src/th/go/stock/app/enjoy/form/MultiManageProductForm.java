package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ProductmasterBean;

public class MultiManageProductForm {
	
	public static final String NEW 	= "NEW";
	public static final String UPD 	= "UPD";
	public static final String DEL 	= "DEL";
	
	private String 						productTypeCode;
	private String 						productTypeName;
	private String 						productGroupCode;
	private String 						productGroupName;
	private String 						unitCode;
	private String 						unitName;
	private List<ProductmasterBean> 	productList;
	private String						errMsg;
	private String						seqTemp;
	private boolean						chk;
	
	public MultiManageProductForm(){
		this.productTypeCode		= "";
		this.productTypeName		= "";
		this.productGroupCode		= "";
		this.productGroupName		= "";
		this.unitCode				= "";
		this.unitName				= "";
		this.productList			= new ArrayList<ProductmasterBean>();
		this.errMsg					= "";
		this.seqTemp				= "0";
		this.chk					= false;
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

	public List<ProductmasterBean> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductmasterBean> productList) {
		this.productList = productList;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getSeqTemp() {
		return seqTemp;
	}

	public void setSeqTemp(String seqTemp) {
		this.seqTemp = seqTemp;
	}

	public boolean isChk() {
		return chk;
	}

	public void setChk(boolean chk) {
		this.chk = chk;
	}
}
