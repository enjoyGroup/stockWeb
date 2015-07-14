package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the subdistrict database table.
 * 
 */
@Entity
@NamedQuery(name="Subdistrict.findAll", query="SELECT s FROM Subdistrict s")
public class Subdistrict implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String subdistrictId;

	private String districtId;

	private String provinceId;

	private String subdistrictName;

	public Subdistrict() {
	}

	public String getSubdistrictId() {
		return this.subdistrictId;
	}

	public void setSubdistrictId(String subdistrictId) {
		this.subdistrictId = subdistrictId;
	}

	public String getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getSubdistrictName() {
		return this.subdistrictName;
	}

	public void setSubdistrictName(String subdistrictName) {
		this.subdistrictName = subdistrictName;
	}

}