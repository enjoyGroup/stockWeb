package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.RefconstantcodeBean;

public class RefconstantcodeMaintananceForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	public static final String DEL 	= "DEL";
	
	private List<RefconstantcodeBean> 			refconstantcodeList;
	private String								titlePage;
	
	public RefconstantcodeMaintananceForm(){
		this.refconstantcodeList 		= new ArrayList<RefconstantcodeBean>();
		this.titlePage					= "";
	}

	public List<RefconstantcodeBean> getRefconstantcodeList() {
		return refconstantcodeList;
	}

	public void setRefconstantcodeList(List<RefconstantcodeBean> refconstantcodeList) {
		this.refconstantcodeList = refconstantcodeList;
	}

	public String getTitlePage() {
		return titlePage;
	}

	public void setTitlePage(String titlePage) {
		this.titlePage = titlePage;
	}
}
