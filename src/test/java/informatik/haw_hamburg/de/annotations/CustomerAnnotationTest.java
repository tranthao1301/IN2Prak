package informatik.haw_hamburg.de.annotations;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class CustomerAnnotationTest {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static long[] ids;
	private static String[] firstNames;
	private static String[] lastNames;
	private static List<Customer> customers;
	
   @BeforeAll	
	public static void getConnection() {
	emf = Persistence.createEntityManagerFactory("persistenceUnit2");
	em = emf.createEntityManager();
		assertTrue(em != null);
		customers = new ArrayList<Customer>();
		ids = new long[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
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
		Query delete = em.createNativeQuery("DELETE FROM CUSTOMER2");
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
	 public void checkCustomerData() {
		System.out.println("########################################################");
		System.out.println("DOING CHECK");
	
	 assertTrue(em != null);
	 assertTrue(customers != null);
	 em.getTransaction().begin();
	 TypedQuery<Customer> select = em.createQuery(" FROM CUSTOMER2", Customer.class);	
	 List<Customer> result = select.getResultList();	 
	 for(Customer c: result) {
		 System.out.println((c));
		 }
	 for(Customer c: customers) {
		 assertTrue(result.contains(c));
		 }
	 
		System.out.println("All in it!");
	 
	 em.getTransaction().commit();
	 System.out.println("########################################################");
		System.out.println("CHECK END");
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
	
	@Test
	public void delete(){
		System.out.println("########################################################");
		System.out.println("DOING DELETETEST");
		em.getTransaction().begin();
		
		TypedQuery<Customer> select = em.createQuery(" FROM CUSTOMER2", Customer.class);

		 List<Customer> result = select.getResultList();	
		 
	
		for(Customer c: result) {
			 System.out.println((c));
			 }
		 for(Customer c: customers) {
			 assertTrue(result.contains(c));
			 }
		
	    Query res = em.createQuery("DELETE FROM CUSTOMER2 WHERE FIRSTNAME='Paul'");
		res.executeUpdate();
		
		 List<Customer> rs = select.getResultList();	
	     assertFalse(rs.isEmpty());
	
	//cardinality of Customers in DB
		Query number = em.createNativeQuery("SELECT Count(CUSTOMERID) FROM CUSTOMER2");
		
		BigDecimal size = (BigDecimal) number.getSingleResult();
		
		System.out.println(size);
		assertEquals(size.intValue(),  9);
		
		em.getTransaction().commit();
		System.out.println("########################################################");
		System.out.println(" DELETETEST END");
	}
	
	@AfterAll
	public static void shutdown(){
		em.close();
		emf.close();
	}
}
