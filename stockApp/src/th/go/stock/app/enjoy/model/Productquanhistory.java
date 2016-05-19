package th.go.stock.app.enjoy.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the productquanhistory database table.
 * 
 */
@Entity
@NamedQuery(name="Productquanhistory.findAll", query="SELECT p FROM Productquanhistory p")
public class Productquanhistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int hisCode;

	private String formRef;

	private String hisDate;

	private String productCode;

	private String productGroup;

	private String productType;

	private BigDecimal quantityMinus;

	private BigDecimal quantityPlus;

	private BigDecimal quantityTotal;

	private String tin;

	public Productquanhistory() {
	}

	public int getHisCode() {
		return this.hisCode;
	}

	public void setHisCode(int hisCode) {
		this.hisCode = hisCode;
	}

	public String getFormRef() {
		return this.formRef;
	}

	public void setFormRef(String formRef) {
		this.formRef = formRef;
	}

	public String getHisDate() {
		return this.hisDate;
	}

	public void setHisDate(String hisDate) {
		this.hisDate = hisDate;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductGroup() {
		return this.productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public BigDecimal getQuantityMinus() {
		return this.quantityMinus;
	}

	public void setQuantityMinus(BigDecimal quantityMinus) {
		this.quantityMinus = quantityMinus;
	}

	public BigDecimal getQuantityPlus() {
		return this.quantityPlus;
	}

	public void setQuantityPlus(BigDecimal quantityPlus) {
		this.quantityPlus = quantityPlus;
	}

	public BigDecimal getQuantityTotal() {
		return this.quantityTotal;
	}

	public void setQuantityTotal(BigDecimal quantityTotal) {
		this.quantityTotal = quantityTotal;
	}

	public String getTin() {
		return this.tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

}