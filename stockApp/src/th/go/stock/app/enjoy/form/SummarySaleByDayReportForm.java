package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.SummarySaleByDayReportBean;

public class SummarySaleByDayReportForm {
	
	private SummarySaleByDayReportBean 			summarySaleByDayReportBean;
	private String								errMsg;
	private String								titlePage;
	private List<SummarySaleByDayReportBean> 	dataList;
	private List<ComboBean> 					companyCombo;
	
	public SummarySaleByDayReportForm(){
		this.summarySaleByDayReportBean 	= new SummarySaleByDayReportBean();
		this.errMsg							= "";
		this.titlePage						= "";
		this.dataList						= new ArrayList<SummarySaleByDayReportBean>();
		this.companyCombo					= new ArrayList<ComboBean>();
	}

	public SummarySaleByDayReportBean getSummarySaleByDayReportBean() {
		return summarySaleByDayReportBean;
	}

	public void setSummarySaleByDayReportBean(
			SummarySaleByDayReportBean summarySaleByDayReportBean) {
		this.summarySaleByDayReportBean = summarySaleByDayReportBean;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getTitlePage() {
		return titlePage;
	}

	public void setTitlePage(String titlePage) {
		this.titlePage = titlePage;
	}

	public List<SummarySaleByDayReportBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<SummarySaleByDayReportBean> dataList) {
		this.dataList = dataList;
	}

	public List<ComboBean> getCompanyCombo() {
		return companyCombo;
	}

	public void setCompanyCombo(List<ComboBean> companyCombo) {
		this.companyCombo = companyCombo;
	}
}
