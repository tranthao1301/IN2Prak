package informatik.haw_hamburg.de.IN2Prak;

import java.time.LocalDate;

public class Customer {
	long _customerID;
	String _firstName;
	String _familyName;
	LocalDate _entryDate;

	public Customer(long id, String first, String last, LocalDate entryDate) {
		_customerID = id;
		_firstName = first;
		_familyName = last;
		_entryDate = entryDate;
	}
	
	public long getID() {
		return _customerID;
	}
	
	public String getFirstName() {
		return _firstName;
	}
	
	public String getFamilyName() {
		return _familyName;
	}
	
	public LocalDate getEntryDate() {
		return _entryDate;
	}
 
}
