package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.SummarySaleByCustomerReportBean;

public class SummarySaleByCustomerReportForm {
	
	private SummarySaleByCustomerReportBean 		summarySaleByCustomerReportBean;
	private String									errMsg;
	private String									titlePage;
	private List<SummarySaleByCustomerReportBean> 	dataList;
	
	public SummarySaleByCustomerReportForm(){
		this.summarySaleByCustomerReportBean 	= new SummarySaleByCustomerReportBean();
		this.errMsg								= "";
		this.titlePage							= "";
		this.dataList							= new ArrayList<SummarySaleByCustomerReportBean>();
	}

	public SummarySaleByCustomerReportBean getSummarySaleByCustomerReportBean() {
		return summarySaleByCustomerReportBean;
	}

	public void setSummarySaleByCustomerReportBean(
			SummarySaleByCustomerReportBean summarySaleByCustomerReportBean) {
		this.summarySaleByCustomerReportBean = summarySaleByCustomerReportBean;
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

	public List<SummarySaleByCustomerReportBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<SummarySaleByCustomerReportBean> dataList) {
		this.dataList = dataList;
	}
}
