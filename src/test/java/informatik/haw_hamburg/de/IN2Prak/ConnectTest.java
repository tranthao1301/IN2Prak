package informatik.haw_hamburg.de.IN2Prak;

import java.time.LocalDate;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ConnectTest {
	CustomerDAOImpl customerDAO = new CustomerDAOImpl();
//	Connect c = new Connect();
//	Connection con = c.connect();
	
	void createTable() {
		Statement stm = null;
		try {
			stm = customerDAO.con.createStatement();
			stm.executeUpdate("CREATE TABLE ABO816.CUSTOMER (CUSTOMERID NUMBER NOT NULL, FIRSTNAME VARCHAR(50),"
								+ "FAMILYNAME VARCHAR(50), ENTRYDATE DATE, PRIMARY KEY(CUSTOMERID))");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stm != null) {
					stm.close();
				}
//				if(customerDAO.con != null) {
//					customerDAO.con.close();
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	void addCustomersTest() {
		deleteTable();
		createTable();
		LocalDate date = LocalDate.now();
		
		List<Customer> list = new ArrayList<>();
		list.add(new Customer(1,"Paul","Kowalski",date));
		list.add(new Customer(2,"Paulina","Petersen",date));
		list.add(new Customer(3,"Max","Mustermann",date));
		list.add(new Customer(4,"Melanie","Miller",date));
		list.add(new Customer(5,"Oscar","Peters",date));
		list.add(new Customer(6,"Scarlet","Johanson",date));
		list.add(new Customer(7,"Raymond","Reddington",date));
		list.add(new Customer(8,"Elizabeth","Keen",date));
		list.add(new Customer(9,"Thomas","Cooper",date));
		list.add(new Customer(10,"Samar","Navabi",date));
		
		
		customerDAO.insertCustomerList(list);
		
		assertEquals(customerDAO.showCustomerByID(1), "Paul Kowalski");
		customerDAO.showCustomer();
		System.out.println();
		customerDAO.updateCustomer(1, "Selena", "Gomez", date);
		assertEquals(customerDAO.showCustomerByID(1), "Selena Gomez");
		customerDAO.showCustomer();
		System.out.println();
		customerDAO.deleteCustomer(1);
		customerDAO.showCustomer();
		try {
			if(customerDAO.con != null) {
				customerDAO.con.close();
			}
		} catch (SQLException e) {
				e.printStackTrace();
			}
		
//		customerDAO.deleteCustomers();
	}
	
//	@Test
//	void addCustomerTest() {
//		Date date = new Date(System.currentTimeMillis());
//		Customer customer = new Customer(12,"John","Doe",date);
//		customerDAO.insertCustomer(customer);
//		customerDAO.showCustomerByID(12);
//	}
	
	void deleteTable() {
		Statement stm = null;
		try {
			stm = customerDAO.con.createStatement();
			stm.executeUpdate("DROP TABLE ABO816.CUSTOMER");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stm != null) {
					stm.close();
				}
//				if(customerDAO.con != null) {
//					customerDAO.con.close();
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
