package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the invoicecreditmaster database table.
 * 
 */
@Embeddable
public class InvoicecreditmasterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String invoiceCode;

	private String tin;

	public InvoicecreditmasterPK() {
	}
	public String getInvoiceCode() {
		return this.invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public String getTin() {
		return this.tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InvoicecreditmasterPK)) {
			return false;
		}
		InvoicecreditmasterPK castOther = (InvoicecreditmasterPK)other;
		return 
			this.invoiceCode.equals(castOther.invoiceCode)
			&& this.tin.equals(castOther.tin);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.invoiceCode.hashCode();
		hash = hash * prime + this.tin.hashCode();
		
		return hash;
	}
}