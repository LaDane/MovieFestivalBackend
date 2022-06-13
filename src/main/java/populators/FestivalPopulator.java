package populators;

import entities.Festival;
import facades.FestivalFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;

public class FestivalPopulator {
    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        populate(emf);
    }

    public static void populate(EntityManagerFactory emf) {
        Festival f1 = new Festival(
                "Festival1",
                "City1",
                LocalDateTime.of(2022, 1, 1, 14, 30),
                LocalDateTime.of(2022, 1, 2, 12, 15)
        );
        Festival f2 = new Festival(
                "Festival2",
                "City2",
                LocalDateTime.of(2022, 2, 1, 14, 30),
                LocalDateTime.of(2022, 2, 2, 12, 15)
        );
        Festival f3 = new Festival(
                "Festival3",
                "City3",
                LocalDateTime.of(2022, 3, 1, 14, 30),
                LocalDateTime.of(2022, 3, 2, 12, 15)
        );

        FestivalFacade ff = FestivalFacade.getFacade(emf);
        ff.create(f1);
        ff.create(f2);
        ff.create(f3);
    }
}
