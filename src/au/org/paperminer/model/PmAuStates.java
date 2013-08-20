package au.org.paperminer.model;

// Generated Aug 21, 2013 12:00:35 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PmAuStates generated by hbm2java
 */
@Entity
@Table(name = "pm_au_states", catalog = "paperminer")
public class PmAuStates implements java.io.Serializable {

	private byte id;
	private String shortName;
	private String longName;

	public PmAuStates() {
	}

	public PmAuStates(byte id, String longName) {
		this.id = id;
		this.longName = longName;
	}

	public PmAuStates(byte id, String shortName, String longName) {
		this.id = id;
		this.shortName = shortName;
		this.longName = longName;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public byte getId() {
		return this.id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	@Column(name = "short_name", length = 3)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "long_name", nullable = false, length = 30)
	public String getLongName() {
		return this.longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

}
