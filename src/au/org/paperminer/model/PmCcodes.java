package au.org.paperminer.model;

// Generated Aug 21, 2013 12:00:35 AM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * PmCcodes generated by hbm2java
 */
@JsonAutoDetect
@Entity
@Table(name = "pm_ccodes", catalog = "paperminer")
public class PmCcodes implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4686001443106436626L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@Column(name = "short_name", length = 2)
	private String shortName;
	@Column(name = "long_name", nullable = false, length = 64)
	private String longName;

	public PmCcodes() {
	}

	public PmCcodes(int id, String longName) {
		this.id = id;
		this.longName = longName;
	}

	public PmCcodes(int id, String shortName, String longName) {
		this.id = id;
		this.shortName = shortName;
		this.longName = longName;
	}

	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	
	public String getLongName() {
		return this.longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

}
