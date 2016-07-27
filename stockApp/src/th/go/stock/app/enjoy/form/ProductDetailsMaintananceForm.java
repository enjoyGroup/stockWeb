package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ProductmasterBean;
import th.go.stock.app.enjoy.bean.ProductdetailBean;

public class ProductDetailsMaintananceForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	public static final String DEL 	= "DEL";
	
	
	private ProductmasterBean 			productmasterBean;
	private List<ComboBean> 			statusCombo;
	private String						errMsg;
	private String						pageMode;
	private String						titlePage;
	private List<ProductdetailBean> 	productdetailList;
	private String						seqTemp;
	
	public ProductDetailsMaintananceForm(){
		this.productmasterBean 		= new ProductmasterBean();
		this.statusCombo			= new ArrayList<ComboBean>();
		this.errMsg					= "";
		this.pageMode				= NEW;
		this.titlePage				= "";
		this.productdetailList		= new ArrayList<ProductdetailBean>();
		this.seqTemp				= "0";
	}

	public ProductmasterBean getProductmasterBean() {
		return productmasterBean;
	}

	public void setProductmasterBean(ProductmasterBean productmasterBean) {
		this.productmasterBean = productmasterBean;
	}

	public List<ComboBean> getStatusCombo() {
		return statusCombo;
	}

	public void setStatusCombo(List<ComboBean> statusCombo) {
		this.statusCombo = statusCombo;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getPageMode() {
		return pageMode;
	}

	public void setPageMode(String pageMode) {
		this.pageMode = pageMode;
	}

	public String getTitlePage() {
		return titlePage;
	}

	public void setTitlePage(String titlePage) {
		this.titlePage = titlePage;
	}

	public List<ProductdetailBean> getProductdetailList() {
		return productdetailList;
	}

	public void setProductdetailList(List<ProductdetailBean> productdetailList) {
		this.productdetailList = productdetailList;
	}

	public String getSeqTemp() {
		return seqTemp;
	}

	public void setSeqTemp(String seqTemp) {
		this.seqTemp = seqTemp;
	}
}
