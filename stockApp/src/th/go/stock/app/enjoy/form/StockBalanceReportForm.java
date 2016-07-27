package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.StockBalanceReportBean;

public class StockBalanceReportForm {
	
	private StockBalanceReportBean 			stockBalanceReportBean;
	private String							errMsg;
	private String							titlePage;
	private List<StockBalanceReportBean> 	dataList;
	
	public StockBalanceReportForm(){
		this.stockBalanceReportBean 	= new StockBalanceReportBean();
		this.errMsg						= "";
		this.titlePage					= "";
		this.dataList					= new ArrayList<StockBalanceReportBean>();
	}

	public StockBalanceReportBean getStockBalanceReportBean() {
		return stockBalanceReportBean;
	}

	public void setStockBalanceReportBean(
			StockBalanceReportBean stockBalanceReportBean) {
		this.stockBalanceReportBean = stockBalanceReportBean;
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

	public List<StockBalanceReportBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<StockBalanceReportBean> dataList) {
		this.dataList = dataList;
	}
}
