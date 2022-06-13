package facades;

import Helpers.CreateEntity;
import Helpers.ResetDB;
import entities.Guest;
import entities.Show;
import errorhandling.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import populators.RolePopulator;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFacadeTest {

    private static EntityManagerFactory emf;
    private static GuestFacade facade;
    private CreateEntity ce;

    public GuestFacadeTest() {}

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = GuestFacade.getFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        ResetDB.truncate(emf);
    }

    @BeforeEach
    public void setUp() {
        ResetDB.truncate(emf);
        RolePopulator.populateRoles(emf);
        ce = new CreateEntity();
    }

    @Test
    void create() {
        Guest guest = ce.createTestGuest();
        Guest createdGuest = facade.create(guest);
        assertEquals(1, createdGuest.getId());
    }

    @Test
    void update() throws NotFoundException {
        Guest guest = ce.createTestGuest();
        Guest createdGuest = facade.create(guest);

        createdGuest.setName("NewGuest");
        createdGuest.setEmail("new@guest.com");

        Show show = ce.createTestShow();
        createdGuest.addShow(show);

        Guest updatedGuest = facade.update(guest);

        assertEquals("NewGuest", updatedGuest.getName());
        assertEquals("new@guest.com", updatedGuest.getEmail());
        assertEquals("TestShow", updatedGuest.getShowList().get(0).getName());
    }

    @Test
    void delete() throws NotFoundException {
        Guest guest = ce.createTestGuest();
        facade.create(guest);
        assertEquals(1, facade.getCount());
        facade.delete(1L);
        assertEquals(0, facade.getCount());
    }

    @Test
    void getById() throws NotFoundException {
        Guest guest = ce.createTestGuest();
        facade.create(guest);

        Guest foundGuest = facade.getById(1L);
        assertEquals("TestGuest", foundGuest.getName());
    }

    @Test
    void getAll() {
        Guest guest = ce.createTestGuest();
        facade.create(guest);

        List<Guest> guestList = facade.getAll();
        assertEquals(1, guestList.size());
    }

    @Test
    void getCount() {
        Guest guest = ce.createTestGuest();
        facade.create(guest);

        assertEquals(1, facade.getCount());
    }

    @Test
    void getGuestShowList() throws NotFoundException {
        Guest guest = ce.createTestGuest();
        Guest createdGuest = facade.create(guest);

        Show show = ce.createTestShow();
        createdGuest.addShow(show);
        Guest updatedGuest = facade.update(createdGuest);

        assertEquals("TestShow", updatedGuest.getShowList().get(0).getName());
    }

    @Test
    void signupShow() throws NotFoundException {
        Guest guest = ce.createTestGuest();
        Guest createdGuest = facade.create(guest);

        Show show = ce.createTestShow();
        createdGuest.addShow(show);
        Guest updatedGuest = facade.update(createdGuest);

        Show guestShow = updatedGuest.getShowList().get(0);

        assertEquals("TestShow", guestShow.getName());
    }
}