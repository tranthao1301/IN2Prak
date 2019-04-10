package informatik.haw_hamburg.de.IN2Prak;

import java.sql.Date;

public class Customer {
	long _customerID;
	String _firstName;
	String _familyName;
	Date _entryDate;

	public Customer(long id, String first, String last, Date entryDate) {
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
	
	public Date getEntryDate() {
		return _entryDate;
	}
 
}
