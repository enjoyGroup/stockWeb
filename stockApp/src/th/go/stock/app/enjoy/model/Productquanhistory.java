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

	@EmbeddedId
	private ProductquanhistoryPK id;

	private String formRef;

	private String hisDate;

	private int productCode;

	private int productGroup;

	private int productType;

	private BigDecimal quantityMinus;

	private BigDecimal quantityPlus;

	private BigDecimal quantityTotal;

	public Productquanhistory() {
	}

	public ProductquanhistoryPK getId() {
		return this.id;
	}

	public void setId(ProductquanhistoryPK id) {
		this.id = id;
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

	public int getProductCode() {
		return this.productCode;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

	public int getProductGroup() {
		return this.productGroup;
	}

	public void setProductGroup(int productGroup) {
		this.productGroup = productGroup;
	}

	public int getProductType() {
		return this.productType;
	}

	public void setProductType(int productType) {
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

}