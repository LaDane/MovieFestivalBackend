package populators;

import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

public class Populator {
    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        populate(emf);
    }

    public static void populate(EntityManagerFactory emf){
        RolePopulator.populateRoles(emf);
        FestivalPopulator.populate(emf);
        GuestPopulator.populate(emf);
        ShowPopulator.populate(emf);
    }
}
