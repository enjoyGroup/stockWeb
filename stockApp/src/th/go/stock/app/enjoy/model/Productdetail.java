package th.go.stock.app.enjoy.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the productdetail database table.
 * 
 */
@Entity
@NamedQuery(name="Productdetail.findAll", query="SELECT p FROM Productdetail p")
public class Productdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProductdetailPK id;
	
	private String availPageFlag;

	private BigDecimal discountRate;

	private String expDate;

	private BigDecimal quanDiscount;

	private String startDate;

	public Productdetail() {
	}

	public ProductdetailPK getId() {
		return this.id;
	}

	public void setId(ProductdetailPK id) {
		this.id = id;
	}

	public BigDecimal getDiscountRate() {
		return this.discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	public String getExpDate() {
		return this.expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public BigDecimal getQuanDiscount() {
		return this.quanDiscount;
	}

	public void setQuanDiscount(BigDecimal quanDiscount) {
		this.quanDiscount = quanDiscount;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getAvailPageFlag() {
		return availPageFlag;
	}

	public void setAvailPageFlag(String availPageFlag) {
		this.availPageFlag = availPageFlag;
	}

}