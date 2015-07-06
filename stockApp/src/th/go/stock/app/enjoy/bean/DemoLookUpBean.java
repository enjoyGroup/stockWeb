package th.go.stock.app.enjoy.bean;


public class DemoLookUpBean {
	
	private String	comboCode;
	private String	comboDesc;
	
	private String 	pageCodes;
	private String 	pageNames;
	private String	pathPages;
	
	
	public DemoLookUpBean(){
		this.comboCode		= "";
		this.comboDesc		= "";
		
		this.pageCodes		= "";
		this.pageNames		= "";
		this.pathPages		= "";
		
	}
	
	public DemoLookUpBean(String comboCode, String comboDesc){
		this.comboCode		= comboCode;
		this.comboDesc		= comboDesc;
	}


	public String getComboCode() {
		return comboCode;
	}


	public void setComboCode(String comboCode) {
		this.comboCode = comboCode;
	}


	public String getComboDesc() {
		return comboDesc;
	}


	public void setComboDesc(String comboDesc) {
		this.comboDesc = comboDesc;
	}


	public String getPageCodes() {
		return pageCodes;
	}


	public void setPageCodes(String pageCodes) {
		this.pageCodes = pageCodes;
	}


	public String getPageNames() {
		return pageNames;
	}


	public void setPageNames(String pageNames) {
		this.pageNames = pageNames;
	}


	public String getPathPages() {
		return pathPages;
	}


	public void setPathPages(String pathPages) {
		this.pathPages = pathPages;
	}

	
}
