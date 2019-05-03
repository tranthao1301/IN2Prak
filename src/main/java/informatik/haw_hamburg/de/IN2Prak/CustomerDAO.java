package informatik.haw_hamburg.de.IN2Prak;

import java.time.LocalDate;
import java.util.List;

/**
 * Zugriff auf die Tabelle CUSTOMER.
 */
public interface CustomerDAO {


	/**
	 * Füge diesen Customer in die Datenbanktabelle CUSTOMER ein.
	 * 
	 * @param customer
	 *            Der einzufügende Customer.
	 */
	void insertCustomer(Customer customer);
	
	void insertCustomerList(List<Customer> list);

	/**
	 * Selektiere alle Customer aus der Datenbanktabelle CUSTOMER.
	 * 
	 */
	void showCustomer();

	/**
	 * Lösche alle Customers aus der Datenbanktabelle CUSTOMER.
	 * 
	 */
	void deleteCustomer(long id);

	/**
	 * Ändere diesen Customer auf der Datenbanktabelle CUSTOMER. 
	 * 
	 * @param id das zu ändernde ID
	 * @return true, wenn der Customer geändert wurde; false, wenn nicht
	 */
	void updateCustomer(long id, String firstName, String familyName, LocalDate entryDate);

}