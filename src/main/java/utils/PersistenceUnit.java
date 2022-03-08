package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUnit {

	private static final String REMOTE = "SQL";
	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static EntityManagerFactory getInstance() {
                    try{
                        emf = Persistence.createEntityManagerFactory("SQL");
                    }catch(Exception e){
                        System.out.println("Error " + e.getLocalizedMessage());
                    }
			
		
		return emf;
	}

	public static EntityManager getEM() {
		if (em == null) {
			try{
                            em = PersistenceUnit.getInstance().createEntityManager();
                        }catch(Exception e){
                            System.out.println("Error: " + e);
                        }
		}
		return em;
	}
	
}
