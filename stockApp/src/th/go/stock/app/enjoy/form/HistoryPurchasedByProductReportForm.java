package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.HistoryPurchasedByProductReportBean;

public class HistoryPurchasedByProductReportForm {
	
	private HistoryPurchasedByProductReportBean 		historyPurchasedByProductReportBean;
	private String										errMsg;
	private String										titlePage;
	private List<HistoryPurchasedByProductReportBean> 	dataList;
	private List<ComboBean> 							companyCombo;
	
	public HistoryPurchasedByProductReportForm(){
		this.historyPurchasedByProductReportBean 	= new HistoryPurchasedByProductReportBean();
		this.errMsg									= "";
		this.titlePage								= "";
		this.dataList								= new ArrayList<HistoryPurchasedByProductReportBean>();
		this.companyCombo							= new ArrayList<ComboBean>();
	}

	public HistoryPurchasedByProductReportBean getHistoryPurchasedByProductReportBean() {
		return historyPurchasedByProductReportBean;
	}

	public void setHistoryPurchasedByProductReportBean(
			HistoryPurchasedByProductReportBean historyPurchasedByProductReportBean) {
		this.historyPurchasedByProductReportBean = historyPurchasedByProductReportBean;
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

	public List<HistoryPurchasedByProductReportBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<HistoryPurchasedByProductReportBean> dataList) {
		this.dataList = dataList;
	}

	public List<ComboBean> getCompanyCombo() {
		return companyCombo;
	}

	public void setCompanyCombo(List<ComboBean> companyCombo) {
		this.companyCombo = companyCombo;
	}
}
