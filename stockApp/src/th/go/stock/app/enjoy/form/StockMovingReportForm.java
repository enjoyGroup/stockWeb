package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ProductQuanHistoryBean;

public class StockMovingReportForm {
	
	private ProductQuanHistoryBean 			productQuanHistoryBean;
	private String							errMsg;
	private String							titlePage;
	private List<ProductQuanHistoryBean> 	dataList;
	private List<ComboBean> 				companyCombo;
	
	public StockMovingReportForm(){
		this.productQuanHistoryBean 	= new ProductQuanHistoryBean();
		this.errMsg								= "";
		this.titlePage							= "";
		this.dataList							= new ArrayList<ProductQuanHistoryBean>();
		this.companyCombo						= new ArrayList<ComboBean>();
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

	public List<ComboBean> getCompanyCombo() {
		return companyCombo;
	}

	public void setCompanyCombo(List<ComboBean> companyCombo) {
		this.companyCombo = companyCombo;
	}
}
