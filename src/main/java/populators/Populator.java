package populators;

import javax.persistence.EntityManagerFactory;

import errorhandling.NotFoundException;
import utils.EMF_Creator;

public class Populator {
    public static void main(String[] args) throws NotFoundException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        populate(emf);
    }

    public static void populate(EntityManagerFactory emf) throws NotFoundException {
        RolePopulator.populateRoles(emf);
        FestivalPopulator.populate(emf);
        GuestPopulator.populate(emf);
        ShowPopulator.populate(emf);
    }
}
