package informatik.haw_hamburg.de.IN2Prak;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
	private Connect c = new Connect();
	public Connection con = c.connect();

	
	public void insertCustomer(Customer customer){
		String command = "INSERT INTO ABO816.CUSTOMER (CUSTOMERID,FIRSTNAME,FAMILYNAME,ENTRYDATE)" 
							+  "VALUES (?,?,?,?)";
		
		try {
			PreparedStatement stm = con.prepareStatement(command);
			stm.setLong(1, customer.getID());
			stm.setString(2, customer.getFirstName());
			stm.setString(3, customer.getFamilyName());
			stm.setDate(4, Date.valueOf(customer.getEntryDate()));
			stm.executeUpdate();
			stm.close();
			System.out.println("Customer was added succesfully!");
		} catch (SQLException e) {
			System.err.println("Could not add Customer!");
		}
			
	}

	public void insertCustomerList(List<Customer> list) {
		int count = 0;
		String command = "INSERT INTO ABO816.CUSTOMER (CUSTOMERID,FIRSTNAME,FAMILYNAME,ENTRYDATE)" +  "VALUES (?,?,?,?)";
		try(PreparedStatement stm = con.prepareStatement(command);){

			for(Customer c : list) {
				stm.setLong(1, c.getID());
				stm.setString(2, c.getFirstName());
				stm.setString(3, c.getFamilyName());
				stm.setDate(4, Date.valueOf(c.getEntryDate()));
				
				stm.addBatch();
				count++;
				if(count == list.size()) {
					stm.executeBatch();
				}
				System.out.println("All Customers have been succesfully added to the table!");
//				con.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteCustomer(long id) {
		String command = "DELETE FROM ABO816.CUSTOMER WHERE CUSTOMERID = ?";
		try{
			PreparedStatement stm = con.prepareStatement(command);
			stm.setLong(1, id);
			stm.executeUpdate();
			stm.close();
			System.out.println("Customer has been succesfully removed from the table!");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void updateCustomer(long id, String firstName, String familyName, LocalDate entryDate) {
		String query = "UPDATE ABO816.CUSTOMER SET firstname = ?, familyname = ?, entrydate = ? WHERE customerid = ? ";
		try {
			PreparedStatement stm = con.prepareStatement(query);
			stm.setString(1, firstName); 
			stm.setString(2, familyName);
			stm.setDate(3, Date.valueOf(entryDate));
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
		} catch (SQLException e) {
			System.err.println("Error retrieving Customer Data" + e.getMessage());
		}		
	}

	
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
