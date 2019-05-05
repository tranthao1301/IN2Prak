package informatik.haw_hamburg.de.Locking;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity(name="CUSTOMLOCK")
@Table(name="CUSTOMLOCK")

public class Customer {

	@Id
	@Column(name="CUSTOMERID")
	private long id;
	@Basic
	@Column(name="FIRSTNAME", length=50)
	private String firstName;
	@Basic
	@Column(name="FAMILYNAME", length=50)
	private String lastName;
	@Basic
	@Column(name="ENTRYDATE")
	private Date entryDate;
	
	@Version
	@Column(name="OPTLOCK")
	private long optlock;
	
	public Customer (long id, String firstName, String lastName, Date entryDate ) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.entryDate = entryDate;
	}//constructor()

	public Customer() {
	
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", entryDate=" + entryDate
				+ "]";
	}

}
