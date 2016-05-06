package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the relationuserncompany database table.
 * 
 */
@Entity
@NamedQuery(name="Relationuserncompany.findAll", query="SELECT r FROM Relationuserncompany r")
public class Relationuserncompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RelationuserncompanyPK id;

	public Relationuserncompany() {
	}

	public RelationuserncompanyPK getId() {
		return this.id;
	}

	public void setId(RelationuserncompanyPK id) {
		this.id = id;
	}

}