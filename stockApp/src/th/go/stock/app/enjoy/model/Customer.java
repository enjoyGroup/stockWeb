package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the customer database table.
 * 
 */
@Entity
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int cusCode;

	private String birthDate;

	private String buildingName;

	private String cusName;

	private String cusStatus;

	private String cusSurname;

	private String districtCode;

	private String email;

	private String expDate;

	private String fax;

	private String houseNumber;

	private String idNumber;

	private String idType;

	private String job;

	private String mooNumber;

	private int point;

	private String postCode;

	private String provinceCode;

	private String religion;

	private String remark;

	private String sex;

	private String soiName;

	private String startDate;

	private String streetName;

	private String subdistrictCode;

	private String tel;

	public Customer() {
	}

	public int getCusCode() {
		return this.cusCode;
	}

	public void setCusCode(int cusCode) {
		this.cusCode = cusCode;
	}

	public String getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getBuildingName() {
		return this.buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusStatus() {
		return this.cusStatus;
	}

	public void setCusStatus(String cusStatus) {
		this.cusStatus = cusStatus;
	}

	public String getCusSurname() {
		return this.cusSurname;
	}

	public void setCusSurname(String cusSurname) {
		this.cusSurname = cusSurname;
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

	public String getExpDate() {
		return this.expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
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

	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdType() {
		return this.idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getMooNumber() {
		return this.mooNumber;
	}

	public void setMooNumber(String mooNumber) {
		this.mooNumber = mooNumber;
	}

	public int getPoint() {
		return this.point;
	}

	public void setPoint(int point) {
		this.point = point;
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

	public String getReligion() {
		return this.religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSoiName() {
		return this.soiName;
	}

	public void setSoiName(String soiName) {
		this.soiName = soiName;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
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

}