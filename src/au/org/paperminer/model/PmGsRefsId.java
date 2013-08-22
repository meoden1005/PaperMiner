package au.org.paperminer.model;

// Generated Aug 21, 2013 12:00:35 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * PmGsRefsId generated by hbm2java
 */
@Embeddable
public class PmGsRefsId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1892438153478044571L;
	@Column(name = "trove_id", nullable = false)
	private int troveId;
	@Column(name = "location_id", nullable = false)
	private int locationId;

	public PmGsRefsId() {
	}

	public PmGsRefsId(int troveId, int locationId) {
		this.troveId = troveId;
		this.locationId = locationId;
	}

	
	public int getTroveId() {
		return this.troveId;
	}

	public void setTroveId(int troveId) {
		this.troveId = troveId;
	}

	
	public int getLocationId() {
		return this.locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PmGsRefsId))
			return false;
		PmGsRefsId castOther = (PmGsRefsId) other;

		return (this.getTroveId() == castOther.getTroveId())
				&& (this.getLocationId() == castOther.getLocationId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getTroveId();
		result = 37 * result + this.getLocationId();
		return result;
	}

}
