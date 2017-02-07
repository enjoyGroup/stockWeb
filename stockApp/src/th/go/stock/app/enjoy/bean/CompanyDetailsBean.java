package th.go.stock.app.enjoy.bean;



public class CompanyDetailsBean {
	
	private String tin;
	private String companyName;
	private String branchName;
	private String buildingName;
	private String houseNumber;
	private String mooNumber;
	private String soiName;
	private String streetName;
	private String subdistrictName;
	private String subdistrictCode;
	private String districtName;
	private String districtCode;
	private String provinceName;
	private String provinceCode;
	private String postCode;
	private String tel;
	private String fax;
	private String email;
	private String remark;
	private String companyStatus;
	private String companyStatusName;
	private String seqDis;
	
	public CompanyDetailsBean(){
		this.tin 				= "";
		this.companyName 		= "";
		this.branchName 		= "";
		this.buildingName 		= "";
		this.houseNumber 		= "";
		this.mooNumber 			= "";
		this.soiName 			= "";
		this.streetName 		= "";
		this.subdistrictName 	= "";
		this.subdistrictCode 	= "";
		this.districtName 		= "";
		this.districtCode 		= "";
		this.provinceName 		= "";
		this.provinceCode 		= "";
		this.postCode 			= "";
		this.tel 				= "";
		this.fax 				= "";
		this.email 				= "";
		this.remark 			= "";
		this.companyStatus 		= "";
		this.companyStatusName 	= "";
		this.seqDis				= "1";
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getMooNumber() {
		return mooNumber;
	}

	public void setMooNumber(String mooNumber) {
		this.mooNumber = mooNumber;
	}

	public String getSoiName() {
		return soiName;
	}

	public void setSoiName(String soiName) {
		this.soiName = soiName;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getSubdistrictName() {
		return subdistrictName;
	}

	public void setSubdistrictName(String subdistrictName) {
		this.subdistrictName = subdistrictName;
	}

	public String getSubdistrictCode() {
		return subdistrictCode;
	}

	public void setSubdistrictCode(String subdistrictCode) {
		this.subdistrictCode = subdistrictCode;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getCompanyStatusName() {
		return companyStatusName;
	}

	public void setCompanyStatusName(String companyStatusName) {
		this.companyStatusName = companyStatusName;
	}

	public String getAddress() {
		
		String address = "";
		
		try{
			
			if(!houseNumber.equals("")){
				address += houseNumber;
			}
			
			if(!mooNumber.equals("")){
				address += " หมู่ที่ " + mooNumber;
			}
			
			if(!buildingName.equals("")){
				address += " อาคาร " + buildingName;
			}
			
			if(!soiName.equals("")){
				address += " ซอย" + soiName;
			}
			
			if(!streetName.equals("")){
				address += " ถนน" + streetName;
			}
			
			if(!subdistrictName.equals("")){
				if(provinceCode.equals("10")){
					address += " แขวง " + subdistrictName;
				}else{
					address += " ตำบล " + subdistrictName;
				}
			}
			
			if(!districtName.equals("")){
				if(provinceCode.equals("10")){
					address += " เขต " + districtName;
				}else{
					address += " อำเภอ " + districtName;
				}
			}
			
			if(!provinceName.equals("")){
				address += " " + provinceName;
			}
			
			if(!postCode.equals("")){
				address += " " + postCode;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return address;
	}

	public String getSeqDis() {
		return seqDis;
	}

	public void setSeqDis(String seqDis) {
		this.seqDis = seqDis;
	}

	
}
