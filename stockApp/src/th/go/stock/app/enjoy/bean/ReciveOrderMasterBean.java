package th.go.stock.app.enjoy.bean;

public class ReciveOrderMasterBean {
	
	private String reciveNo;
	private String reciveDate;
	private String reciveType;
	private String creditDay;
	private String creditExpire;
	private String vendorCode;
	private String branchName;
	private String billNo;
	private String priceType;
	private String reciveStatus;
	private String reciveStatusDesc;
	private String userUniqueId;
	private String reciveAmount;
	private String reciveDiscount;
	private String reciveVat;
	private String reciveTotal;
	private String reciveDateFrom;
	private String reciveDateTo;
	private String usrName;
	private String tin;
	private String seqDis;
	
	public ReciveOrderMasterBean(){
		this.reciveNo 			= "";
		this.reciveDate 		= "";
		this.reciveType 		= "";
		this.creditDay 			= "";
		this.creditExpire 		= "";
		this.vendorCode 		= "";
		this.branchName 		= "";
		this.billNo 			= "";
		this.priceType 			= "";
		this.reciveStatus 		= "";
		this.reciveStatusDesc 	= "";
		this.userUniqueId 		= "";
		this.reciveAmount 		= "0.00";
		this.reciveDiscount 	= "0.00";
		this.reciveVat 			= "0.00";
		this.reciveTotal 		= "0.00";
		this.reciveDateFrom 	= "";
		this.reciveDateTo		= "";
		this.usrName			= "";
		this.tin				= "";
		this.seqDis				= "";
	}

	public String getReciveNo() {
		return reciveNo;
	}

	public void setReciveNo(String reciveNo) {
		this.reciveNo = reciveNo;
	}

	public String getReciveDate() {
		return reciveDate;
	}

	public void setReciveDate(String reciveDate) {
		this.reciveDate = reciveDate;
	}

	public String getReciveType() {
		return reciveType;
	}

	public void setReciveType(String reciveType) {
		this.reciveType = reciveType;
	}

	public String getCreditDay() {
		return creditDay;
	}

	public void setCreditDay(String creditDay) {
		this.creditDay = creditDay;
	}

	public String getCreditExpire() {
		return creditExpire;
	}

	public void setCreditExpire(String creditExpire) {
		this.creditExpire = creditExpire;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getReciveStatus() {
		return reciveStatus;
	}

	public void setReciveStatus(String reciveStatus) {
		this.reciveStatus = reciveStatus;
	}

	public String getUserUniqueId() {
		return userUniqueId;
	}

	public void setUserUniqueId(String userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

	public String getReciveAmount() {
		return reciveAmount;
	}

	public void setReciveAmount(String reciveAmount) {
		this.reciveAmount = reciveAmount;
	}

	public String getReciveDiscount() {
		return reciveDiscount;
	}

	public void setReciveDiscount(String reciveDiscount) {
		this.reciveDiscount = reciveDiscount;
	}

	public String getReciveVat() {
		return reciveVat;
	}

	public void setReciveVat(String reciveVat) {
		this.reciveVat = reciveVat;
	}

	public String getReciveTotal() {
		return reciveTotal;
	}

	public void setReciveTotal(String reciveTotal) {
		this.reciveTotal = reciveTotal;
	}

	public String getReciveDateFrom() {
		return reciveDateFrom;
	}

	public void setReciveDateFrom(String reciveDateFrom) {
		this.reciveDateFrom = reciveDateFrom;
	}

	public String getReciveDateTo() {
		return reciveDateTo;
	}

	public void setReciveDateTo(String reciveDateTo) {
		this.reciveDateTo = reciveDateTo;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getReciveStatusDesc() {
		return reciveStatusDesc;
	}

	public void setReciveStatusDesc(String reciveStatusDesc) {
		this.reciveStatusDesc = reciveStatusDesc;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getSeqDis() {
		return seqDis;
	}

	public void setSeqDis(String seqDis) {
		this.seqDis = seqDis;
	}
}
