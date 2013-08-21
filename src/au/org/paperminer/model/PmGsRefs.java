package au.org.paperminer.model;

// Generated Aug 21, 2013 12:00:35 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PmGsRefs generated by hbm2java
 */
@Entity
@Table(name = "pm_gs_refs", catalog = "paperminer")
public class PmGsRefs implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4774878365324448766L;
	private PmGsRefsId id;
	private PmLocations pmLocations;
	private byte strikeout;
	private short frequency;
	private Date dateCreated;

	public PmGsRefs() {
	}

	public PmGsRefs(PmGsRefsId id, PmLocations pmLocations, byte strikeout,
			short frequency, Date dateCreated) {
		this.id = id;
		this.pmLocations = pmLocations;
		this.strikeout = strikeout;
		this.frequency = frequency;
		this.dateCreated = dateCreated;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "troveId", column = @Column(name = "trove_id", nullable = false)),
			@AttributeOverride(name = "locationId", column = @Column(name = "location_id", nullable = false)) })
	public PmGsRefsId getId() {
		return this.id;
	}

	public void setId(PmGsRefsId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id", nullable = false, insertable = false, updatable = false)
	public PmLocations getPmLocations() {
		return this.pmLocations;
	}

	public void setPmLocations(PmLocations pmLocations) {
		this.pmLocations = pmLocations;
	}

	@Column(name = "strikeout", nullable = false)
	public byte getStrikeout() {
		return this.strikeout;
	}

	public void setStrikeout(byte strikeout) {
		this.strikeout = strikeout;
	}

	@Column(name = "frequency", nullable = false)
	public short getFrequency() {
		return this.frequency;
	}

	public void setFrequency(short frequency) {
		this.frequency = frequency;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = false, length = 19)
	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}
