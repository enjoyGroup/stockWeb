package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the productmaster database table.
 * 
 */
@Entity
@NamedQuery(name="Productmaster.findAll", query="SELECT p FROM Productmaster p")
public class Productmaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProductmasterPK id;

	private BigDecimal costPrice;

	private BigDecimal minQuan;

	private String productCodeDis;

	private int productGroup;

	private String productName;

	private String productStatus;

	private int productType;

	private BigDecimal salePrice1;

	private BigDecimal salePrice2;

	private BigDecimal salePrice3;

	private BigDecimal salePrice4;

	private BigDecimal salePrice5;

	private int unitCode;

	public Productmaster() {
	}

	public ProductmasterPK getId() {
		return this.id;
	}

	public void setId(ProductmasterPK id) {
		this.id = id;
	}

	public BigDecimal getCostPrice() {
		return this.costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getMinQuan() {
		return this.minQuan;
	}

	public void setMinQuan(BigDecimal minQuan) {
		this.minQuan = minQuan;
	}

	public String getProductCodeDis() {
		return this.productCodeDis;
	}

	public void setProductCodeDis(String productCodeDis) {
		this.productCodeDis = productCodeDis;
	}

	public int getProductGroup() {
		return this.productGroup;
	}

	public void setProductGroup(int productGroup) {
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

	public int getProductType() {
		return this.productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	public BigDecimal getSalePrice1() {
		return this.salePrice1;
	}

	public void setSalePrice1(BigDecimal salePrice1) {
		this.salePrice1 = salePrice1;
	}

	public BigDecimal getSalePrice2() {
		return this.salePrice2;
	}

	public void setSalePrice2(BigDecimal salePrice2) {
		this.salePrice2 = salePrice2;
	}

	public BigDecimal getSalePrice3() {
		return this.salePrice3;
	}

	public void setSalePrice3(BigDecimal salePrice3) {
		this.salePrice3 = salePrice3;
	}

	public BigDecimal getSalePrice4() {
		return this.salePrice4;
	}

	public void setSalePrice4(BigDecimal salePrice4) {
		this.salePrice4 = salePrice4;
	}

	public BigDecimal getSalePrice5() {
		return this.salePrice5;
	}

	public void setSalePrice5(BigDecimal salePrice5) {
		this.salePrice5 = salePrice5;
	}

	public int getUnitCode() {
		return this.unitCode;
	}

	public void setUnitCode(int unitCode) {
		this.unitCode = unitCode;
	}

}
