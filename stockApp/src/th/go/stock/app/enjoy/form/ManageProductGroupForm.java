package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ManageProductGroupBean;

public class ManageProductGroupForm {
	
	public static final String NEW 	= "NEW";
	public static final String UPD 	= "UPD";
	public static final String DEL 	= "DEL";
	
	private String 							productTypeCode;
	private String 							productTypeName;
	private List<ManageProductGroupBean> 	productGroupList;
	private String							errMsg;
	private String							seqTemp;
	private boolean							chk;
	
	public ManageProductGroupForm(){
		this.productTypeCode		= "";
		this.productTypeName		= "";
		this.productGroupList		= new ArrayList<ManageProductGroupBean>();
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

	public List<ManageProductGroupBean> getProductGroupList() {
		return productGroupList;
	}

	public void setProductGroupList(List<ManageProductGroupBean> productGroupList) {
		this.productGroupList = productGroupList;
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
