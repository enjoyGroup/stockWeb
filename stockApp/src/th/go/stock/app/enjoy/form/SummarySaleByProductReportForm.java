package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.SummarySaleByProductReportBean;

public class SummarySaleByProductReportForm {
	
	private SummarySaleByProductReportBean 			summarySaleByProductReportBean;
	private String									errMsg;
	private String									titlePage;
	private List<SummarySaleByProductReportBean> 	dataList;
	
	public SummarySaleByProductReportForm(){
		this.summarySaleByProductReportBean 	= new SummarySaleByProductReportBean();
		this.errMsg								= "";
		this.titlePage							= "";
		this.dataList							= new ArrayList<SummarySaleByProductReportBean>();
	}

	public SummarySaleByProductReportBean getSummarySaleByProductReportBean() {
		return summarySaleByProductReportBean;
	}

	public void setSummarySaleByProductReportBean(
			SummarySaleByProductReportBean summarySaleByProductReportBean) {
		this.summarySaleByProductReportBean = summarySaleByProductReportBean;
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

	public List<SummarySaleByProductReportBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<SummarySaleByProductReportBean> dataList) {
		this.dataList = dataList;
	}
}
