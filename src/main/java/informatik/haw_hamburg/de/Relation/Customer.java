package informatik.haw_hamburg.de.Relation;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQuery(
			name="findCustomersByName",
			query="FROM CUSTOMERR c WHERE c.firstName LIKE:custName")
@Entity(name="CUSTOMERR")
public class Customer {
	
	public Customer() {
		
	}
	
	@Id
	private long id;
	private String firstName;
	private String lastName;
	private Date entryDate;
	
	private Address address;
	private List<Bank> banks;
	private List<CreditCard> cards;
	
	public Customer (long id, String firstName, String lastName, Date entryDate ) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.entryDate = entryDate;
	}//constructor()

	
	@Column(name="CUSTOMERID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="FIRSTNAME", length=50)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name="FAMILYNAME", length=50)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name="ENTRYDATE")
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	@ManyToOne
	@JoinColumn(name="ADDRESSID", nullable=false)
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@ManyToMany
	@JoinTable(name="VISITS", joinColumns=@JoinColumn(name="CUSTOMERID", referencedColumnName="CUSTOMERID"),
				inverseJoinColumns=@JoinColumn(name="BANKID", referencedColumnName="BANKID"))
	public List<Bank> getBanks(){
		return banks;
	}
	
	public void addBank(Bank bank) {
		this.banks.add(bank);
	}
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="customer")
	public List<CreditCard> getCards(){
		return cards;
	}
	
	public void addCard(CreditCard card) {
		this.cards.add(card);
	}
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", entryDate=" + entryDate
				+ "]";
	}


}
