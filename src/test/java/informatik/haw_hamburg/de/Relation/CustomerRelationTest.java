package informatik.haw_hamburg.de.Relation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerRelationTest {
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	private static List<Address> addresses;	
	private static List<CreditCard> creditCards;

	private static List<Bank> banks;
	
	private static List<Customer> customers;
	private static long[] ids;
	
	@BeforeAll
	public static void connectDatabase(){
		emf = Persistence.createEntityManagerFactory("persistenceUnit2");
		em = emf.createEntityManager();
		
		setupIds();
		setupAddresses();
		setupCreditCards();
		setupBanks();
		setupCustomers();
		
		setupCreditCardsCustomer();
		giveAddressesCustomers();
		setupCustomersOnBanks();
	}
	
    @BeforeEach
//	@Test
	public void setUp(){
		if(em.getTransaction().isActive()) {
			em.getTransaction().rollback();
		}
		//delete leftover problems from the db
		em.getTransaction().begin();
		Query deleteBankCust = em.createNativeQuery("DELETE FROM VISITS");
		deleteBankCust.executeUpdate();
		Query deleteBank = em.createNativeQuery("DELETE FROM BANK");
		deleteBank.executeUpdate();
		Query deleteCreditCard = em.createNativeQuery("DELETE FROM CREDITCARD");
		deleteCreditCard.executeUpdate();
		Query deleteCust = em.createNativeQuery("DELETE FROM CUSTOMERR");
		deleteCust.executeUpdate();
		Query deleteAddress = em.createNativeQuery("DELETE FROM ADDRESS");
		deleteAddress.executeUpdate();
		em.getTransaction().commit();
		
		
		//refresh entries
		em.getTransaction().begin();
		
		for(Address a : addresses){
			em.persist(a);
		}
		
		for(Customer c: customers){
			em.persist(c);
		}
		for(CreditCard c : creditCards){
			em.persist(c);
		}
		for(Bank b : banks){
			em.persist(b);
		}

		em.getTransaction().commit();
		
	}
	
  //checking if every Data Object is really in the DB
//	@Test
	public void checkCorrectData() {
		em.getTransaction().begin();
		TypedQuery<Customer> select = em.createQuery(" FROM CUSTOMERR" , Customer.class);
		List<Customer> result = select.getResultList();		
		for(Customer c: customers) {
		assertTrue(result.contains(c));
		}
		TypedQuery<Bank> select2 = em.createQuery("FROM BANK" , Bank.class);		
		List<Bank> result2 = select2.getResultList();
		for(Bank b: banks) {
			assertTrue(result2.contains(b));
			}
		
		TypedQuery<CreditCard> select3 = em.createQuery("FROM CREDITCARD" , CreditCard.class);		
		List<CreditCard> result3 = select3.getResultList();
		for(CreditCard c: creditCards) {
			assertTrue(result3.contains(c));
			}
		TypedQuery<Address> select4 = em.createQuery("FROM ADDRESS" , Address.class);		
		List<Address> result4 = select4.getResultList();
		for(Address a: addresses) {
			assertTrue(result4.contains(a));
			}		
		em.getTransaction().commit();
	}
	
//	@Test
	public void deleteTest() {
		em.getTransaction().begin();
		em.createQuery(" DELETE FROM CUSTOMERR WHERE FIRSTNAME = 'JANE'");		
		em.getTransaction().commit();
		em.getTransaction().begin();
		TypedQuery<Customer> all = em.createQuery(" FROM CUSTOMERR" , Customer.class);
		Customer jane = customers.get(0);
		//get sure jane is gone
		assertTrue(jane.getFirstName().equals("Jane"));
		assertFalse(all.getResultList().contains(jane));		
		em.getTransaction().commit();
	}
	
//	@Test
	public void changeUserInformation() {		
		em.getTransaction().begin();
		TypedQuery<Customer> all = em.createQuery("FROM CUSTOMERR", Customer.class);
		List<Customer> custs = all.getResultList();
		for(Customer c: custs) {
			c.setLastName("Dörte");
			em.persist(c);
		}
		em.getTransaction().commit();
		
		
		em.getTransaction().begin();
		TypedQuery<Customer> all2 = em.createQuery("FROM CUSTOMERR", Customer.class);
		List<Customer> custs2 = all2.getResultList();
		//should be different customers
		for(Customer c: custs2) {
			assertFalse(customers.contains(c));
		}
		em.getTransaction().commit();
	}
	
	
	
	@Test
	public void getFromCustoToBank() {
		System.out.println("################################################################################");
		System.out.println("0");	
		em.getTransaction().begin();
		System.out.println("1");
		String q =" SELECT BANKID, NAME" + 
				"  FROM BANK" + 
				" LEFT OUTER JOIN VISITS" + 
				"  ON BANK.BANKID = VISITS.BANKID" + 
				" LEFT OUTER JOIN CUSTOMERR" + 
				"  ON VISITS.CUSTOMID = 6";
//		TypedQuery<Bank> banks= 
		em.createQuery(q, Bank.class);
//		for(Bank b :bankes.getResultList()) {
//			System.out.println(b);
//			assertTrue(customers.get(5).getBanks().contains(b));
//		} 
		
//		@SuppressWarnings("unchecked")
//		List<Bank> l = bankes.getResultList();	
//				
				em.getTransaction().commit();
	}
	

//	@AfterAll
	public static void shutdown(){
		em.close();
		emf.close();
	}
	
	//##################################################################################################################################################
	//##################################################################################################################################################
	// here only setups
	
	
	
	//ids to give as keys
		private static void setupIds() {
			ids = new long[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };		
		}
		
		
		//create customers for use in the db
	     private static void setupCustomers() {
	    	 customers = new ArrayList<Customer>();
	    	 String[] names = {"Jane", "Ana", "Christian", "Tara", "Paul", "Jana", "Robert", "Mila", "Lana",
	 				"Jovan"};
	    	 String[] surnames = {"Milbradt", "Kurnikova", "Bild", "Schmidt", "Hietel", "Clarsen", "Katzenberger",
	 				"Fonda", "Jovanov", "Angeleski" };
	    	 for(int i=0; i<ids.length; i++) {
	    		 customers.add(new Customer(ids[i],names[i], surnames[i], new Date(System.currentTimeMillis())));
	    	 }    	 
		}
	     
	     
	     //create the credit cards for use in the db
	     private static  void setupCreditCards() {
	    	long[] creditCardNumbers = {1050L, 2050L, 3050L, 4050L, 5050L, 6050L, 7050L, 8050L, 9050L,10050L};
	    	creditCards = new ArrayList<CreditCard>();
	    	
	    	for(int i=0; i<creditCardNumbers.length;i++) {
	    	creditCards.add(new CreditCard(creditCardNumbers[i]));
	    	}
	 	}
	     
	     
	   //create the Banks for use in the db
	     private static  void setupBanks() {
	    	 banks = new ArrayList<Bank>();
	 		String[] bankNames= { "HamVoba", "Haspa", "Deutsche Bank", "PSD Bank Kiel", "Targobank", "Sparda", "HypoVereinsbank", "ING-DiBa", "Volksbank Lüneburger Heide", "Commerzbank"};
	 		for(int i=0; i<ids.length;i++) {
	 			banks.add(new Bank(ids[i],bankNames[i]));
	 		}
	 	}
	     
	     
	     //create the Addresses for use in the db
	     private static  void setupAddresses() {
	 		String[] streetNames = {"Straßburgerstraße","Dernauerstraße","Hindenburgstraße","Richterstraße","Wandsbeker Marktstraße","Auerhahnstraße","Krausestraße","Prinzenweg","Berliner Tor", "Hinter dem Tore"};
	 		
	 		addresses = new ArrayList<Address>();
	 		for(int i=0; i<ids.length; i++) {
	 			addresses.add(new Address(ids[i],streetNames[i],i, 20000+(10*i+2*i)));
	 		}
	 		
	 	}

	   //give each Bank a customer and vice versa
		private static  void setupCustomersOnBanks() {		
			for(int i=0; i<ids.length;i++) {
				banks.get(i).addCustomer(customers.get(ids.length-i-1));
				customers.get(ids.length-i-1).addBank(banks.get(i));
			}
		}
		
		//give each Address a customer and vice versa
		private static  void giveAddressesCustomers() {	
		for(int i=0; i<ids.length;i++) {
			customers.get(i).setAddress(addresses.get(ids.length-i-1));
			addresses.get(ids.length-i-1).addCustomer(customers.get(i));
		}
		}
		

		//give each card a customer and vice versa
		private static  void setupCreditCardsCustomer() {
			for(int i=0; i<ids.length;i++) {
				customers.get(i).addCards(creditCards.get(ids.length-i-1));
				creditCards.get(ids.length-i-1).setCustomer(customers.get(i));
			}
			
		}

	
}
