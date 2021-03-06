package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.HistoryPurchasedByDealerReportBean;

public class HistoryPurchasedByDealerReportForm {
	
	private HistoryPurchasedByDealerReportBean 			historyPurchasedByDealerReportBean;
	private String										errMsg;
	private String										titlePage;
	private List<HistoryPurchasedByDealerReportBean> 	dataList;
	
	public HistoryPurchasedByDealerReportForm(){
		this.historyPurchasedByDealerReportBean 	= new HistoryPurchasedByDealerReportBean();
		this.errMsg									= "";
		this.titlePage								= "";
		this.dataList								= new ArrayList<HistoryPurchasedByDealerReportBean>();
	}

	public HistoryPurchasedByDealerReportBean getHistoryPurchasedByDealerReportBean() {
		return historyPurchasedByDealerReportBean;
	}

	public void setHistoryPurchasedByDealerReportBean(
			HistoryPurchasedByDealerReportBean historyPurchasedByDealerReportBean) {
		this.historyPurchasedByDealerReportBean = historyPurchasedByDealerReportBean;
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

	public List<HistoryPurchasedByDealerReportBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<HistoryPurchasedByDealerReportBean> dataList) {
		this.dataList = dataList;
	}
}
