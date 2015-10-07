package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the companyvendor database table.
 * 
 */
@Entity
@NamedQuery(name="Companyvendor.findAll", query="SELECT c FROM Companyvendor c")
public class Companyvendor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int vendorCode;

	private String branchName;

	private String buildingName;

	private String districtCode;

	private String email;

	private String fax;

	private String houseNumber;

	private String mooNumber;

	private String postCode;

	private String provinceCode;

	private String remark;

	private String soiName;

	private String streetName;

	private String subdistrictCode;

	private String tel;

	private String tin;

	private String vendorName;

	public Companyvendor() {
	}

	public int getVendorCode() {
		return this.vendorCode;
	}

	public void setVendorCode(int vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBuildingName() {
		return this.buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getDistrictCode() {
		return this.districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHouseNumber() {
		return this.houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getMooNumber() {
		return this.mooNumber;
	}

	public void setMooNumber(String mooNumber) {
		this.mooNumber = mooNumber;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSoiName() {
		return this.soiName;
	}

	public void setSoiName(String soiName) {
		this.soiName = soiName;
	}

	public String getStreetName() {
		return this.streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getSubdistrictCode() {
		return this.subdistrictCode;
	}

	public void setSubdistrictCode(String subdistrictCode) {
		this.subdistrictCode = subdistrictCode;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTin() {
		return this.tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

}