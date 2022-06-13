package facades;

import entities.Show;
import errorhandling.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class ShowFacade implements IFacade<Show> {

    private static EntityManagerFactory emf;
    private static ShowFacade instance;

    private ShowFacade() {}

    public static ShowFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ShowFacade();
        }
        return instance;
    }

    @Override
    public Show create(Show show) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(show);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return show;
    }

    @Override
    public Show update(Show show) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Show found = em.find(Show.class, show.getId());
        if (found == null) {
            throw new NotFoundException("Entity with ID: " + show.getId() + " not found");
        }

        // TODO: update values here

        try {
            em.getTransaction().begin();
            Show updated = em.merge(show);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    @Override
    public Show delete(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Show found = em.find(Show.class, id);
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
    public Show getById(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Show show;
        try {
            show = em.find(Show.class, id);
            if (show == null) {
                throw new NotFoundException();
            }
        } finally {
            em.close();
        }
        return show;
    }

    @Override
    public List<Show> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Show> query = em.createQuery("SELECT z FROM Show z", Show.class);
        return query.getResultList();
    }

    @Override
    public long getCount() {
        EntityManager em = emf.createEntityManager();
        try{
            return (Long)em.createQuery("SELECT COUNT(z) FROM Show z").getSingleResult();
        } finally {
            em.close();
        }
    }
}
