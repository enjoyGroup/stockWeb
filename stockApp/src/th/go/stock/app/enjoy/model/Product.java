package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String productCode;

	private BigDecimal costPrice;

	private String expDate;

	private BigDecimal minQuan;

	private String productGroup;

	private String productName;

	private String productStatus;

	private String productType;

	private BigDecimal quantity;

	private BigDecimal salePrice;

	private String startDate;

	private int unitCode;

	public Product() {
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getCostPrice() {
		return this.costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public String getExpDate() {
		return this.expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public BigDecimal getMinQuan() {
		return this.minQuan;
	}

	public void setMinQuan(BigDecimal minQuan) {
		this.minQuan = minQuan;
	}

	public String getProductGroup() {
		return this.productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductStatus() {
		return this.productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getUnitCode() {
		return this.unitCode;
	}

	public void setUnitCode(int unitCode) {
		this.unitCode = unitCode;
	}

}