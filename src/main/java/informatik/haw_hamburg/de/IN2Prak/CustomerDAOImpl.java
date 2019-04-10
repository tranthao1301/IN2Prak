package informatik.haw_hamburg.de.IN2Prak;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
	private Connect c = new Connect();
	public int count = 0;
	public Connection con = c.connect();

	
	public void insertCustomer(Customer customer){
//		long customerID = customer.getID();
//		String firstName = customer.getFirstName();
//		String familyName = customer.getFamilyName();
//		Date entryDate = customer.getEntryDate();
//		String command = "INSERT INTO ABO816.CUSTOMER(" + customerID + "," + firstName +
//				"," + familyName + ")" + "VALUES(" + entryDate + ")";
//		deleteTable();
//		createTable();
		String command = "INSERT INTO ABO816.CUSTOMER (CUSTOMERID,FIRSTNAME,FAMILYNAME,ENTRYDATE)" 
							+  "VALUES (?,?,?,?)";
		
		try {
			PreparedStatement stm = con.prepareStatement(command);
			stm.setLong(1, customer.getID());
			stm.setString(2, customer.getFirstName());
			stm.setString(3, customer.getFamilyName());
			stm.setDate(4, customer.getEntryDate());
			stm.executeUpdate();
			stm.close();
		} catch (SQLException e) {
			System.err.println("Could not add Customer!");
		}
		System.out.println("Customer was added succesfully!");
			
	}

	public void insertCustomerList(List<Customer> list) {
		String command = "INSERT INTO ABO816.CUSTOMER (CUSTOMERID,FIRSTNAME,FAMILYNAME,ENTRYDATE)" +  "VALUES (?,?,?,?)";
		try(PreparedStatement stm = con.prepareStatement(command);){
			
			for(Customer c : list) {
				stm.setLong(1, c.getID());
				stm.setString(2, c.getFirstName());
				stm.setString(3, c.getFamilyName());
				stm.setDate(4, c.getEntryDate());
				
				stm.addBatch();
				count++;
				if(count%100 == 0 || count == list.size()) {
					stm.executeBatch();
				}
//				con.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("All Customers have been succesfully added to the table!");
	}

	public void deleteCustomers() {
		Statement stm = null;
		try{
			stm = con.createStatement();
			stm.execute("DELETE FROM ABO816.CUSTOMER WHERE CUSTOMERID >= 1");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stm.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("All Customers have been succesfully removed from the table!");
	}

	public void updateCustomer(long id, String firstName, String familyName, Date entryDate) {
		String query = "UPDATE ABO816.CUSTOMER SET firstname = ?, familyname = ?, entrydate = ? WHERE customerid = ? ";
		try {
			PreparedStatement stm = con.prepareStatement(query);
			stm.setString(1, firstName); 
			stm.setString(2, familyName);
			stm.setDate(3, entryDate);
			stm.setLong(4, id);
			stm.executeUpdate();
			System.out.println("Database updated succesfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	
	}

	@Override
	public void showCustomer() {
		try {
			Statement stm = con.createStatement();
			String query = "SELECT * FROM ABO816.CUSTOMER";
			ResultSet result = stm.executeQuery(query);
			
			while(result.next()) {
				long customerID = result.getLong(1);
				String first = result.getString(2);
				String family = result.getString(3);
				Date entryDate = result.getDate(4);
				
				System.out.println("Customer:" + customerID + " | " + first + " | " 
									+ family + " | " + entryDate);
			}
			result.close();
			stm.close();
//			if(con != null) {
//				con.close();
//			}
		} catch (SQLException e) {
			System.err.println("Error retrieving Customer Data" + e.getMessage());
		}		
	}

//	public int getCount() {
//		return count;
//	}
	
	public String showCustomerByID(long id) {
		String customerName = null;
		try {
			Statement stm = con.createStatement();
			String query = "SELECT * FROM ABO816.CUSTOMER WHERE CUSTOMERID =" + id;
			ResultSet result = stm.executeQuery(query);
			result.next();
//			long customerID = id;
			String first = result.getString(2);
			String last = result.getString(3);
//			Date entryDate = result.getDate(4);
//			System.out.println("Customer:" + customerID + " | " + first + " | " 
//					+ last + " | " + entryDate);
			customerName = first + " " + last;
			result.close();
			stm.close();
//			con.close();
		 } catch (SQLException e) {
			 System.err.println("Error retrieving Customer Data" + e.getMessage());
		 }
		
		return customerName;
	}

}
