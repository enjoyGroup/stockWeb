package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.AlertLowProductReportBean;

public class AlertLowProductReportForm {
	
	private AlertLowProductReportBean 		alertLowProductReportBean;
	private String							errMsg;
	private String							titlePage;
	private List<AlertLowProductReportBean> dataList;
	
	public AlertLowProductReportForm(){
		this.alertLowProductReportBean 	= new AlertLowProductReportBean();
		this.errMsg						= "";
		this.titlePage					= "";
		this.dataList					= new ArrayList<AlertLowProductReportBean>();
	}

	public AlertLowProductReportBean getAlertLowProductReportBean() {
		return alertLowProductReportBean;
	}

	public void setAlertLowProductReportBean(
			AlertLowProductReportBean alertLowProductReportBean) {
		this.alertLowProductReportBean = alertLowProductReportBean;
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

	public List<AlertLowProductReportBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<AlertLowProductReportBean> dataList) {
		this.dataList = dataList;
	}
}
