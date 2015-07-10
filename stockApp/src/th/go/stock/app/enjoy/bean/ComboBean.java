package th.go.stock.app.enjoy.bean;


public class ComboBean {
	
	private String	code;
	private String	desc;
	
	
	public ComboBean(){
		this.code		= "";
		this.desc		= "";
		
	}
	
	public ComboBean(String code, String desc){
		this.code		= code;
		this.desc		= desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	

	
}
