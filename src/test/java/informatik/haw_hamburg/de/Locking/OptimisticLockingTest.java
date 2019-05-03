package informatik.haw_hamburg.de.Locking;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class OptimisticLockingTest {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static Long[] ids;
	private static String[] firstNames;
	private static String[] lastNames;
	private static List<Customer> customers;
	
   @BeforeAll	
	public static void getConnection() {
	emf = Persistence.createEntityManagerFactory("persistenceUnit");
	em = emf.createEntityManager();
		assertTrue(em != null);
		customers = new ArrayList<Customer>();
		ids = new Long[] { 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L };
		firstNames = new String[] { "Jane", "Ana", "Christian", "Tara", "Paul", "Jana", "Robert", "Mila", "Lana",
				"Jovan" };
		for(int i = 0; i<10;i++) {
			firstNames[i]= firstNames[i];
	
		}
		lastNames = new String[] { "Milbradt", "Kurnikova", "Bild", "Schmidt", "Hietel", "Clarsen", "Katzenberger",
				"Fonda", "Jovanov", "Angeleski" };
		;

		for (int i = 0; i < firstNames.length; i++) {
			Customer newCustomer = new Customer(ids[i], firstNames[i], lastNames[i],
					new Date(System.currentTimeMillis()));
			customers.add(newCustomer);
		}
		em.getTransaction().begin();
	    System.out.println("I DELETE ");
		Query delete = em.createNativeQuery("DELETE FROM CUSTOMLOCK");
		delete.executeUpdate();
		em.getTransaction().commit();
		em.getTransaction().begin();
		//add all customers to the db
		System.out.println("I REWRITE ");
		for (Customer c : customers) {
			em.persist(c);
		}
		em.getTransaction().commit();
		System.out.println("########################################################");
		System.out.println("WRITE END");

	}
   
   @Test
	 public void changeCustomerData(){
		System.out.println("########################################################");
		System.out.println("DOING CHANGE");
	 assertTrue(em != null);
	 //Transparent Update
	 em.getTransaction().begin();
	 Customer jane = em.find(Customer.class, 1L);
	 System.out.println(jane);
	 assertTrue(jane != null);
	 em.detach(jane);
	 jane.setFirstName("Harrald");
	  em.merge(jane);
	  em.getTransaction().commit();
	  jane =em.find(Customer.class, 1L );
		 System.out.println(jane);
	  assertTrue(jane.getFirstName().equals("Harrald"));
	  System.out.println("########################################################");
		System.out.println(" CHANGE END");
	 }
   
}
