package au.org.paperminer.model;

// Generated Aug 21, 2013 12:00:35 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PmAudit generated by hbm2java
 */
@Entity
@Table(name = "pm_audit", catalog = "paperminer")
public class PmAudit implements java.io.Serializable {

	private PmAuditId id;
	private Integer troveId;
	private Integer locationId;
	private char action;

	public PmAudit() {
	}

	public PmAudit(PmAuditId id, char action) {
		this.id = id;
		this.action = action;
	}

	public PmAudit(PmAuditId id, Integer troveId, Integer locationId,
			char action) {
		this.id = id;
		this.troveId = troveId;
		this.locationId = locationId;
		this.action = action;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)),
			@AttributeOverride(name = "dateCreated", column = @Column(name = "date_created", nullable = false, length = 19)) })
	public PmAuditId getId() {
		return this.id;
	}

	public void setId(PmAuditId id) {
		this.id = id;
	}

	@Column(name = "trove_id")
	public Integer getTroveId() {
		return this.troveId;
	}

	public void setTroveId(Integer troveId) {
		this.troveId = troveId;
	}

	@Column(name = "location_id")
	public Integer getLocationId() {
		return this.locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	@Column(name = "action", nullable = false, length = 1)
	public char getAction() {
		return this.action;
	}

	public void setAction(char action) {
		this.action = action;
	}

}
