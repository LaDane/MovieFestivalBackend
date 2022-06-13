package facades;

import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import errorhandling.NotFoundException;
import security.errorhandling.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {}

    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVerifiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public User getUserByName(String username) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null) {
                throw new NotFoundException("No user with this name exists");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public boolean usernameExists(String username) {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
        } finally {
            em.close();
        }
        return user != null;
    }

    public List<String> getAllUsernames() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> tq = em.createQuery("SELECT u FROM User u", User.class);
        List<String> usernames = new ArrayList<>();
        for (User u : tq.getResultList()) {
            usernames.add(u.getUserName());
        }
        return usernames;
    }

    public void grantAdmin() throws NotFoundException {
        User admin = getUserByName("admin");
        Role adminRole = RoleFacade.getRoleFacade(emf).getRoleByName("admin");
        List<Role> roleList = new ArrayList<>();
        roleList.add(adminRole);
        admin.setRoleList(roleList);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(admin);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
