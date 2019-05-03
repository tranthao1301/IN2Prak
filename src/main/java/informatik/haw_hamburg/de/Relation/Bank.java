package informatik.haw_hamburg.de.Relation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(name = "BANK")
public class Bank {

	@Id
	@Column(nullable = false)
	private Long bankId;

	@Basic
	@Column(name = "NAME", nullable = false)
	private String bankName;

	@ManyToMany(mappedBy = "banks")
	// visitors of the bank
	private List<Customer> customerList;

	public Bank(long id, String bankName) {
		this.bankId = id;
		this.bankName = bankName;
		customerList = new ArrayList<Customer>();
	}

	public Bank() {

	}

	public void addCustomer(Customer customer) {
		customerList.add(customer);
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setLocation(String bankName) {
		this.bankName = bankName;
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	@Override
	public String toString() {
		return "Bank [bankId=" + bankId + ", bankName=" + bankName + ", customerList=" + customerList + "]";
	}

}
