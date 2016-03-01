package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the reciveordermaster database table.
 * 
 */
@Entity
@NamedQuery(name="Reciveordermaster.findAll", query="SELECT r FROM Reciveordermaster r")
public class Reciveordermaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String reciveNo;

	private String billNo;

	private String branchName;

	private int creditDay;

	private String creditExpire;

	private String priceType;

	private BigDecimal reciveAmount;

	private String reciveDate;

	private BigDecimal reciveDiscount;

	private String reciveStatus;

	private BigDecimal reciveTotal;

	private String reciveType;

	private BigDecimal reciveVat;

	private int userUniqueId;

	private int vendorCode;
	
	private String tin;

	public Reciveordermaster() {
	}

	public String getReciveNo() {
		return this.reciveNo;
	}

	public void setReciveNo(String reciveNo) {
		this.reciveNo = reciveNo;
	}

	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public int getCreditDay() {
		return this.creditDay;
	}

	public void setCreditDay(int creditDay) {
		this.creditDay = creditDay;
	}

	public String getCreditExpire() {
		return this.creditExpire;
	}

	public void setCreditExpire(String creditExpire) {
		this.creditExpire = creditExpire;
	}

	public String getPriceType() {
		return this.priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public BigDecimal getReciveAmount() {
		return this.reciveAmount;
	}

	public void setReciveAmount(BigDecimal reciveAmount) {
		this.reciveAmount = reciveAmount;
	}

	public String getReciveDate() {
		return this.reciveDate;
	}

	public void setReciveDate(String reciveDate) {
		this.reciveDate = reciveDate;
	}

	public BigDecimal getReciveDiscount() {
		return this.reciveDiscount;
	}

	public void setReciveDiscount(BigDecimal reciveDiscount) {
		this.reciveDiscount = reciveDiscount;
	}

	public String getReciveStatus() {
		return this.reciveStatus;
	}

	public void setReciveStatus(String reciveStatus) {
		this.reciveStatus = reciveStatus;
	}

	public BigDecimal getReciveTotal() {
		return this.reciveTotal;
	}

	public void setReciveTotal(BigDecimal reciveTotal) {
		this.reciveTotal = reciveTotal;
	}

	public String getReciveType() {
		return this.reciveType;
	}

	public void setReciveType(String reciveType) {
		this.reciveType = reciveType;
	}

	public BigDecimal getReciveVat() {
		return this.reciveVat;
	}

	public void setReciveVat(BigDecimal reciveVat) {
		this.reciveVat = reciveVat;
	}

	public int getUserUniqueId() {
		return this.userUniqueId;
	}

	public void setUserUniqueId(int userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

	public int getVendorCode() {
		return this.vendorCode;
	}

	public void setVendorCode(int vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

}