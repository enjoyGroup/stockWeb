package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.SummarySaleByMonthReportBean;

public class SummarySaleByMonthReportForm {
	
	private SummarySaleByMonthReportBean 		summarySaleByMonthReportBean;
	private String								errMsg;
	private String								titlePage;
	private List<SummarySaleByMonthReportBean> 	dataList;
	
	public SummarySaleByMonthReportForm(){
		this.summarySaleByMonthReportBean 	= new SummarySaleByMonthReportBean();
		this.errMsg							= "";
		this.titlePage						= "";
		this.dataList						= new ArrayList<SummarySaleByMonthReportBean>();
	}

	public SummarySaleByMonthReportBean getSummarySaleByMonthReportBean() {
		return summarySaleByMonthReportBean;
	}

	public void setSummarySaleByMonthReportBean(
			SummarySaleByMonthReportBean summarySaleByMonthReportBean) {
		this.summarySaleByMonthReportBean = summarySaleByMonthReportBean;
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

	public List<SummarySaleByMonthReportBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<SummarySaleByMonthReportBean> dataList) {
		this.dataList = dataList;
	}
}
