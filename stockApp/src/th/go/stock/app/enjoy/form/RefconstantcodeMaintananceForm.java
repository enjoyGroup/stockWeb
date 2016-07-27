package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.RefconstantcodeBean;

public class RefconstantcodeMaintananceForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	public static final String DEL 	= "DEL";
	
	private List<RefconstantcodeBean> 			section1List;
	private List<RefconstantcodeBean> 			section2List;
	private String								titlePage;
	
	public RefconstantcodeMaintananceForm(){
		this.section1List 		= new ArrayList<RefconstantcodeBean>();
		this.section2List 		= new ArrayList<RefconstantcodeBean>();
		this.titlePage			= "";
	}

	public List<RefconstantcodeBean> getSection1List() {
		return section1List;
	}

	public void setSection1List(List<RefconstantcodeBean> section1List) {
		this.section1List = section1List;
	}

	public List<RefconstantcodeBean> getSection2List() {
		return section2List;
	}

	public void setSection2List(List<RefconstantcodeBean> section2List) {
		this.section2List = section2List;
	}

	public String getTitlePage() {
		return titlePage;
	}

	public void setTitlePage(String titlePage) {
		this.titlePage = titlePage;
	}
}
