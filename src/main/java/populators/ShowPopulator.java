package populators;

import entities.Show;
import facades.ShowFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;

public class ShowPopulator {
    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        populate(emf);
    }

    public static void populate(EntityManagerFactory emf) {
        Show s1 = new Show(
                "Show1",
                "Location1",
                LocalDateTime.of(2022, 1, 1, 14, 30),
                LocalDateTime.of(2022, 1, 2, 12, 15)
        );
        Show s2 = new Show(
                "Show2",
                "Location2",
                LocalDateTime.of(2022, 2, 1, 14, 30),
                LocalDateTime.of(2022, 2, 2, 12, 15)
        );
        Show s3 = new Show(
                "Show3",
                "Location3",
                LocalDateTime.of(2022, 3, 1, 14, 30),
                LocalDateTime.of(2022, 3, 2, 12, 15)
        );

        ShowFacade sf = ShowFacade.getFacade(emf);
        sf.create(s1);
        sf.create(s2);
        sf.create(s3);
    }
}
