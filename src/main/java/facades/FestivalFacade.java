package facades;

import entities.Festival;
import errorhandling.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class FestivalFacade implements IFacade<Festival> {

    private static EntityManagerFactory emf;
    private static FestivalFacade instance;

    private FestivalFacade() {}

    public static FestivalFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FestivalFacade();
        }
        return instance;
    }

    @Override
    public Festival create(Festival festival) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(festival);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return festival;
    }

    @Override
    public Festival update(Festival festival) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Festival found = em.find(Festival.class, festival.getId());
        if (found == null) {
            throw new NotFoundException("Entity with ID: " + festival.getId() + " not found");
        }

        // TODO: update values here

        try {
            em.getTransaction().begin();
            Festival updated = em.merge(festival);
            em.getTransaction().commit();
            return updated;
        } finally {
            em.close();
        }
    }

    @Override
    public Festival delete(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Festival found = em.find(Festival.class, id);
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
    public Festival getById(Long id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Festival festival;
        try {
            festival = em.find(Festival.class, id);
            if (festival == null) {
                throw new NotFoundException();
            }
        } finally {
            em.close();
        }
        return festival;
    }

    @Override
    public List<Festival> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Festival> query = em.createQuery("SELECT z FROM Festival z", Festival.class);
        return query.getResultList();
    }

    @Override
    public long getCount() {
        EntityManager em = emf.createEntityManager();
        try{
            return (Long)em.createQuery("SELECT COUNT(z) FROM Festival z").getSingleResult();
        } finally {
            em.close();
        }
    }
}
