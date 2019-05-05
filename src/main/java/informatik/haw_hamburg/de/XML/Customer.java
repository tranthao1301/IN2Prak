package informatik.haw_hamburg.de.XML;

import java.sql.Date;

import javax.persistence.Id;

 
public class Customer {
	
	private long id;
	private String firstName;
	private String lastName;
	private Date entryDate;
	
	public Customer (long id, String firstName, String lastName, Date entryDate ) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.entryDate = entryDate;
	}//constructor()

	public Customer() {
	
	}
	
	public Long getId() {
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

