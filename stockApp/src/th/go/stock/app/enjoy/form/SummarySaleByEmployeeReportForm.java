package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.SummarySaleByEmployeeReportBean;

public class SummarySaleByEmployeeReportForm {
	
	private SummarySaleByEmployeeReportBean 		summarySaleByEmployeeReportBean;
	private String									errMsg;
	private String									titlePage;
	private List<SummarySaleByEmployeeReportBean> 	dataList;
	
	public SummarySaleByEmployeeReportForm(){
		this.summarySaleByEmployeeReportBean 	= new SummarySaleByEmployeeReportBean();
		this.errMsg								= "";
		this.titlePage							= "";
		this.dataList							= new ArrayList<SummarySaleByEmployeeReportBean>();
	}

	public SummarySaleByEmployeeReportBean getSummarySaleByEmployeeReportBean() {
		return summarySaleByEmployeeReportBean;
	}

	public void setSummarySaleByEmployeeReportBean(
			SummarySaleByEmployeeReportBean summarySaleByEmployeeReportBean) {
		this.summarySaleByEmployeeReportBean = summarySaleByEmployeeReportBean;
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

	public List<SummarySaleByEmployeeReportBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<SummarySaleByEmployeeReportBean> dataList) {
		this.dataList = dataList;
	}
}
