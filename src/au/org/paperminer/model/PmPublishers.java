package au.org.paperminer.model;

// Generated Aug 21, 2013 12:00:35 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PmPublishers generated by hbm2java
 */
@Entity
@Table(name = "pm_publishers", catalog = "paperminer")
public class PmPublishers implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8290906915042564803L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@Column(name = "title", nullable = false, length = 164)
	private String title;
	@Column(name = "published", nullable = false, length = 12)
	private String published;
	@Column(name = "latitude", nullable = false, length = 16)
	private String latitude;
	@Column(name = "longitude", nullable = false, length = 16)
	private String longitude;
	@Column(name = "location", nullable = false, length = 32)
	private String location;

	public PmPublishers() {
	}

	public PmPublishers(int id, String title, String published,
			String latitude, String longitude, String location) {
		this.id = id;
		this.title = title;
		this.published = published;
		this.latitude = latitude;
		this.longitude = longitude;
		this.location = location;
	}

	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getPublished() {
		return this.published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	
	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	
	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}