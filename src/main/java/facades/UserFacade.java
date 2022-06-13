package facades;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.Guest;
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

    public JsonArray getAllUsers() throws NotFoundException {
        List<String> usernames = getAllUsernames();

        JsonArray ja = new JsonArray();
        for (int i = 0; i < usernames.size(); i++) {
            JsonObject jo = new JsonObject();
            jo.addProperty("id", i+1);
            jo.addProperty("username", usernames.get(i));
            Guest guest = getUserByName(usernames.get(i)).getGuest();
            if (guest != null) {
                jo.addProperty("name", guest.getName());
                jo.addProperty("phone", guest.getPhone());
                jo.addProperty("email", guest.getEmail());
                jo.addProperty("status", guest.getStatus());
                if (guest.getFestival() != null) {
                    jo.addProperty("Festival", guest.getFestival().getName());
                }
            } else {
                jo.addProperty("name", "");
                jo.addProperty("phone", "");
                jo.addProperty("email", "");
                jo.addProperty("status", "");
                jo.addProperty("Festival", "");
            }
            ja.add(jo);
        }
        return ja;
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
