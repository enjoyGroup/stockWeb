package th.go.stock.app.enjoy.bean;


public class PagesDetailBean {
	
	private String 	  pageCodes	  = null;
	private String 	  pageNames	  = null;
	private String	  pathPages   = null;
	
	public PagesDetailBean(){
		this.pageCodes			  = "";
		this.pageNames			  = "";
		this.pathPages			  = "";
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
