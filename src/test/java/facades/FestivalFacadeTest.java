package facades;

import Helpers.CreateEntity;
import Helpers.ResetDB;
import entities.Festival;
import errorhandling.NotFoundException;
import org.junit.jupiter.api.*;
import populators.RolePopulator;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class FestivalFacadeTest {

    private static EntityManagerFactory emf;
    private static FestivalFacade facade;
    private CreateEntity ce;

    public FestivalFacadeTest() {}

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = FestivalFacade.getFacade(emf);
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
        Festival festival = ce.createTestFestival();
        Festival createdFestival = facade.create(festival);
        assertEquals(1, createdFestival.getId());
    }

    @Test
    void update() throws NotFoundException {
        Festival festival = ce.createTestFestival();
        Festival createdFestival = facade.create(festival);
        createdFestival.setName("NewFestival");
        createdFestival.setCity("NewCity");
        Festival updatedFestival = facade.update(createdFestival);

        assertEquals("NewFestival", updatedFestival.getName());
        assertEquals("NewCity", updatedFestival.getCity());
    }

    @Test
    void delete() throws NotFoundException {
        Festival festival = ce.createTestFestival();
        facade.create(festival);
        assertEquals(1, facade.getCount());
        facade.delete(1L);
        assertEquals(0, facade.getCount());
    }

    @Test
    void getById() throws NotFoundException {
        Festival festival = ce.createTestFestival();
        facade.create(festival);

        Festival foundFestival = facade.getById(1L);
        assertEquals("TestFestival", foundFestival.getName());
    }

    @Test
    void getAll() {
        Festival festival = ce.createTestFestival();
        facade.create(festival);

        List<Festival> festivalList = facade.getAll();
        assertEquals(1, festivalList.size());
    }

    @Test
    void getCount() {
        Festival festival = ce.createTestFestival();
        facade.create(festival);

        assertEquals(1, facade.getCount());
    }



//    @Test
//    void testDate() {
//        java.util.Date now = new Date(1111, Calendar.JULY, 1, 22, 22, 22);
//        System.out.println("now = "+ now);
//    }
}