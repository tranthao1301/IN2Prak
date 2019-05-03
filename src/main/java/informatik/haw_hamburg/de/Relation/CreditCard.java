package informatik.haw_hamburg.de.Relation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "CREDITCARD")
public class CreditCard {
	
	@Id
	@Column(name="CARDID", nullable = false)
	private long creditCardNumber;
	
	//user of the card
	@ManyToOne
	@JoinColumn(name="CUSTOMID", nullable=false)
	private Customer customer;
	
	public CreditCard(long cid) {
		this.creditCardNumber = cid;
	}
	
	public CreditCard() {
		
	}

	public long getCardNumber() {
		return creditCardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.creditCardNumber = cardNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "CreditCard [creditCardId=" + creditCardNumber + "]";
	}
}
