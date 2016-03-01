package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CompanyVendorBean;
import th.go.stock.app.enjoy.bean.ReciveOrdeDetailBean;
import th.go.stock.app.enjoy.bean.ReciveOrderMasterBean;
import th.go.stock.app.enjoy.main.ConfigFile;

public class ReciveStockMaintananceForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	public static final String DEL 	= "DEL";
	public final static String VAT 	= ConfigFile.getVat();
	
	
	private ReciveOrderMasterBean 		reciveOrderMasterBean;
	private CompanyVendorBean 			companyVendorBean;
	private List<ComboBean> 			statusCombo;
	private String						errMsg;
	private String						pageMode;
	private String						titlePage;
	private List<ReciveOrdeDetailBean> 	reciveOrdeDetailList;
	private String						seqTemp;
	private List<ComboBean> 			companyCombo;
	
	public ReciveStockMaintananceForm(){
		this.reciveOrderMasterBean 		= new ReciveOrderMasterBean();
		this.companyVendorBean 			= new CompanyVendorBean();
		this.statusCombo				= new ArrayList<ComboBean>();
		this.errMsg						= "";
		this.pageMode					= NEW;
		this.titlePage					= "";
		this.reciveOrdeDetailList		= new ArrayList<ReciveOrdeDetailBean>();
		this.seqTemp					= "0";
		this.companyCombo				= new ArrayList<ComboBean>();
	}

	public ReciveOrderMasterBean getReciveOrderMasterBean() {
		return reciveOrderMasterBean;
	}

	public void setReciveOrderMasterBean(ReciveOrderMasterBean reciveOrderMasterBean) {
		this.reciveOrderMasterBean = reciveOrderMasterBean;
	}

	public CompanyVendorBean getCompanyVendorBean() {
		return companyVendorBean;
	}

	public void setCompanyVendorBean(CompanyVendorBean companyVendorBean) {
		this.companyVendorBean = companyVendorBean;
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

	public List<ReciveOrdeDetailBean> getReciveOrdeDetailList() {
		return reciveOrdeDetailList;
	}

	public void setReciveOrdeDetailList(
			List<ReciveOrdeDetailBean> reciveOrdeDetailList) {
		this.reciveOrdeDetailList = reciveOrdeDetailList;
	}

	public String getSeqTemp() {
		return seqTemp;
	}

	public void setSeqTemp(String seqTemp) {
		this.seqTemp = seqTemp;
	}

	public List<ComboBean> getCompanyCombo() {
		return companyCombo;
	}

	public void setCompanyCombo(List<ComboBean> companyCombo) {
		this.companyCombo = companyCombo;
	}
}
