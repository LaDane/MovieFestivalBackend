package Helpers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ResetDB {
    public static void truncate(EntityManagerFactory emf) {
//        System.out.println("--- TRUNCATING TEST DATABASE ---");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            em.createNativeQuery("truncate table festivals").executeUpdate();
            em.createNativeQuery("truncate table guests").executeUpdate();
            em.createNativeQuery("truncate table RENAMEME").executeUpdate();
            em.createNativeQuery("truncate table roles").executeUpdate();
            em.createNativeQuery("truncate table shows").executeUpdate();
            em.createNativeQuery("truncate table shows_guests").executeUpdate();
            em.createNativeQuery("truncate table user_roles").executeUpdate();
            em.createNativeQuery("truncate table users").executeUpdate();
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        } finally {
            em.close();
        }
    }
}
