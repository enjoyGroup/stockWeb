package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the reciveorder database table.
 * 
 */
@Entity
@NamedQuery(name="Reciveorder.findAll", query="SELECT r FROM Reciveorder r")
public class Reciveorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ReciveorderPK id;

	private String reciveDate;

	private int userUniqueId;

	public Reciveorder() {
	}

	public ReciveorderPK getId() {
		return this.id;
	}

	public void setId(ReciveorderPK id) {
		this.id = id;
	}

	public String getReciveDate() {
		return this.reciveDate;
	}

	public void setReciveDate(String reciveDate) {
		this.reciveDate = reciveDate;
	}

	public int getUserUniqueId() {
		return this.userUniqueId;
	}

	public void setUserUniqueId(int userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

}