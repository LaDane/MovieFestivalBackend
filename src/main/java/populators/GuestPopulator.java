package populators;

import entities.Guest;
import facades.GuestFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;

public class GuestPopulator {
    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        populate(emf);
    }

    public static void populate(EntityManagerFactory emf) {
        Guest g1 = new Guest(
                "Guest1",
                "111",
                "1@guest.com",
                "Attending"
        );
        Guest g2 = new Guest(
                "Guest2",
                "222",
                "2@guest.com",
                "Attending"
        );
        Guest g3 = new Guest(
                "Guest3",
                "333",
                "3@guest.com",
                "Attending"
        );

        GuestFacade gf = GuestFacade.getFacade(emf);
        gf.create(g1);
        gf.create(g2);
        gf.create(g3);
    }
}
