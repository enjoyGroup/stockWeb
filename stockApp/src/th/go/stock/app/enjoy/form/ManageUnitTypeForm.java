package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ManageUnitTypeBean;

public class ManageUnitTypeForm {
	
	public static final String NEW 	= "NEW";
	public static final String UPD 	= "UPD";
	public static final String DEL 	= "DEL";
	
	
	private List<ManageUnitTypeBean> 		unitTypeList;
	private String							errMsg;
	private String							seqTemp;
	
	public ManageUnitTypeForm(){
		this.unitTypeList		= new ArrayList<ManageUnitTypeBean>();
		this.errMsg				= "";
		this.seqTemp			= "0";
	}

	public List<ManageUnitTypeBean> getUnitTypeList() {
		return unitTypeList;
	}

	public void setUnitTypeList(List<ManageUnitTypeBean> unitTypeList) {
		this.unitTypeList = unitTypeList;
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
