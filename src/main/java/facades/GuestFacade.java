package facades;

import entities.Festival;
import entities.Guest;
import entities.Show;
import errorhandling.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class GuestFacade implements IFacade<Guest> {

    private static EntityManagerFactory emf;
    private static GuestFacade instance;

    private GuestFacade() {}

    public static GuestFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GuestFacade();
        }
        return instance;
    }

    @Override
    public Guest create(Guest guest) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(guest);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return guest;
    }

    @Override
    public Guest update(Guest guest) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Guest found = em.find(Guest.class, guest.getId());
        if (found == null) {
            throw new NotFoundException("Entity with ID: " + guest.getId() + " not found");
        }

        // TODO: update values here

        try {
            em.getTransaction().begin();
            Guest updated = em.merge(guest);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    @Override
    public Guest delete(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Guest found = em.find(Guest.class, id);
        if (found == null) {
            throw new NotFoundException("Could not remove Entity with id: " + id);
        }

        try {
            em.getTransaction().begin();
            em.remove(found);
            em.getTransaction().commit();
            return found;
        } finally {
            em.close();
        }
    }

    @Override
    public Guest getById(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Guest guest;
        try {
            guest = em.find(Guest.class, id);
            if (guest == null) {
                throw new NotFoundException();
            }
        } finally {
            em.close();
        }
        return guest;
    }

    @Override
    public List<Guest> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Guest> query = em.createQuery("SELECT z FROM Guest z", Guest.class);
        return query.getResultList();
    }

    @Override
    public long getCount() {
        EntityManager em = emf.createEntityManager();
        try{
            return (Long)em.createQuery("SELECT COUNT(z) FROM Guest z").getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Show> getGuestShowList(Long guestId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Show> query
                = em.createQuery("SELECT s FROM Show s LEFT JOIN s.guestList g WHERE g.id=" + guestId, Show.class);
        return query.getResultList();
    }

    public Guest signupShow(Long guestId, Long showId) throws NotFoundException {
        Guest guest = getById(guestId);
        Show show = ShowFacade.getFacade(emf).getById(showId);
        show.addGuest(guest);
        ShowFacade.getFacade(emf).update(show);
        guest.addShow(show);
        return update(guest);
    }

    public Guest signupFestival(Long guestId, Long festivalId) throws NotFoundException {
        Guest guest = getById(guestId);
        Festival festival = FestivalFacade.getFacade(emf).getById(festivalId);
        guest.setFestival(festival);
        return update(guest);
    }
}
