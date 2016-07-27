package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ProductQuanHistoryBean;

public class StockMovingReportForm {
	
	private ProductQuanHistoryBean 			productQuanHistoryBean;
	private String							errMsg;
	private String							titlePage;
	private List<ProductQuanHistoryBean> 	dataList;
	
	public StockMovingReportForm(){
		this.productQuanHistoryBean 	= new ProductQuanHistoryBean();
		this.errMsg								= "";
		this.titlePage							= "";
		this.dataList							= new ArrayList<ProductQuanHistoryBean>();
	}

	public ProductQuanHistoryBean getProductQuanHistoryBean() {
		return productQuanHistoryBean;
	}

	public void setProductQuanHistoryBean(
			ProductQuanHistoryBean productQuanHistoryBean) {
		this.productQuanHistoryBean = productQuanHistoryBean;
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

	public List<ProductQuanHistoryBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<ProductQuanHistoryBean> dataList) {
		this.dataList = dataList;
	}
}
