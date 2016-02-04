package th.go.stock.app.enjoy.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the adjusthistory database table.
 * 
 */
@Entity
@NamedQuery(name="Adjusthistory.findAll", query="SELECT a FROM Adjusthistory a")
public class Adjusthistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int adjustNo;

	private String adjustDate;

	private String productCode;

	private BigDecimal quanNew;

	private BigDecimal quanOld;

	private String remark;

	public Adjusthistory() {
	}

	public int getAdjustNo() {
		return this.adjustNo;
	}

	public void setAdjustNo(int adjustNo) {
		this.adjustNo = adjustNo;
	}

	public String getAdjustDate() {
		return this.adjustDate;
	}

	public void setAdjustDate(String adjustDate) {
		this.adjustDate = adjustDate;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getQuanNew() {
		return this.quanNew;
	}

	public void setQuanNew(BigDecimal quanNew) {
		this.quanNew = quanNew;
	}

	public BigDecimal getQuanOld() {
		return this.quanOld;
	}

	public void setQuanOld(BigDecimal quanOld) {
		this.quanOld = quanOld;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}