package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ManageProductTypeBean;

public class ManageProductTypeForm {
	
	public static final String NEW 	= "NEW";
	public static final String UPD 	= "UPD";
	public static final String DEL 	= "DEL";
	
	
	private List<ManageProductTypeBean> 	productTypeList;
	private String							errMsg;
	private String							seqTemp;
	
	public ManageProductTypeForm(){
		this.productTypeList		= new ArrayList<ManageProductTypeBean>();
		this.errMsg					= "";
		this.seqTemp				= "0";
	}

	public List<ManageProductTypeBean> getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(List<ManageProductTypeBean> productTypeList) {
		this.productTypeList = productTypeList;
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
}
