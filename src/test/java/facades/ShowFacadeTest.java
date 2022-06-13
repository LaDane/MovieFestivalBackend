package facades;

import Helpers.CreateEntity;
import Helpers.ResetDB;
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

class ShowFacadeTest {

    private static EntityManagerFactory emf;
    private static ShowFacade facade;
    private CreateEntity ce;

    public ShowFacadeTest() {}

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = ShowFacade.getFacade(emf);
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
        Show show = ce.createTestShow();
        Show createdShow = facade.create(show);
        assertEquals(1, createdShow.getId());
    }

    @Test
    void update() throws NotFoundException {
        Show show = ce.createTestShow();
        Show createdShow = facade.create(show);
        createdShow.setName("NewName");
        createdShow.setLocation("NewLocation");
        Show updatedShow = facade.update(createdShow);

        assertEquals("NewName", updatedShow.getName());
        assertEquals("NewLocation", updatedShow.getLocation());
    }

    @Test
    void delete() throws NotFoundException {
        Show show = ce.createTestShow();
        facade.create(show);
        assertEquals(1, facade.getCount());
        facade.delete(1L);
        assertEquals(0, facade.getCount());
    }

    @Test
    void getById() throws NotFoundException {
        Show show = ce.createTestShow();
        facade.create(show);

        Show foundShow = facade.getById(1L);
        assertEquals("TestShow", foundShow.getName());
    }

    @Test
    void getAll() {
        Show show = ce.createTestShow();
        facade.create(show);

        List<Show> showList = facade.getAll();
        assertEquals(1, showList.size());
    }

    @Test
    void getCount() {
        Show show = ce.createTestShow();
        facade.create(show);

        assertEquals(1, facade.getCount());
    }
}