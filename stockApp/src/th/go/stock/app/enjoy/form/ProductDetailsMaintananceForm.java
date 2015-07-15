package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ProductDetailsBean;
import th.go.stock.app.enjoy.bean.ProductDiscountBean;

public class ProductDetailsMaintananceForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	public static final String DEL 	= "DEL";
	
	
	private ProductDetailsBean 			productDetailsBean;
	private List<ComboBean> 			statusCombo;
	private String						errMsg;
	private String						pageMode;
	private String						titlePage;
	private List<ProductDiscountBean> 	productDiscountList;
	private String						seqTemp;
	
	public ProductDetailsMaintananceForm(){
		this.productDetailsBean 	= new ProductDetailsBean();
		this.statusCombo			= new ArrayList<ComboBean>();
		this.errMsg					= "";
		this.pageMode				= NEW;
		this.titlePage				= "";
		this.productDiscountList	= new ArrayList<ProductDiscountBean>();
		this.seqTemp				= "0";
	}

	public ProductDetailsBean getProductDetailsBean() {
		return productDetailsBean;
	}

	public void setProductDetailsBean(ProductDetailsBean productDetailsBean) {
		this.productDetailsBean = productDetailsBean;
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

	public List<ProductDiscountBean> getProductDiscountList() {
		return productDiscountList;
	}

	public void setProductDiscountList(List<ProductDiscountBean> productDiscountList) {
		this.productDiscountList = productDiscountList;
	}

	public String getSeqTemp() {
		return seqTemp;
	}

	public void setSeqTemp(String seqTemp) {
		this.seqTemp = seqTemp;
	}
}
