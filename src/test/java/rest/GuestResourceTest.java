package rest;

import Helpers.CreateEntity;
import Helpers.ResetDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.GuestDTO;
import entities.Guest;
import entities.Show;
import errorhandling.NotFoundException;
import facades.FestivalFacade;
import facades.GuestFacade;
import facades.ShowFacade;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class GuestResourceTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static EntityManagerFactory emf;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    private static GuestFacade facade;
    private CreateEntity ce;

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = GuestFacade.getFacade(emf);

        httpServer = startServer();
        // setup assured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
//        ResetDB.truncate(emf);
    }

    @BeforeEach
    public void setUp() {
        ResetDB.truncate(emf);
        ce = new CreateEntity();
    }

    @Test
    public void testServerStatus() {
        given()
                .when()
                .get("/guest/ping")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreate() {
        Guest guest = ce.createTestGuest();
        String requestBody = GSON.toJson(new GuestDTO(guest));
        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/guest/create")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("TestGuest"));
    }

    @Test
    public void testUpdate() {
        Guest guest = ce.createTestGuest();
        facade.create(guest);

        guest.setName("RestName");
        String requestBody = GSON.toJson(new GuestDTO(guest));

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put("/guest/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("RestName"));
    }

    @Test
    public void testDelete() {
        Guest guest = ce.createTestGuest();
        facade.create(guest);

        given()
                .when()
                .delete("/guest/1");
        expect().statusCode(200)
                .given()
                .when()
                .get("/guest/count")
                .then()
                .assertThat()
                .body("count", equalTo(0));
    }

    @Test
    public void testGetById() {
        Guest guest = ce.createTestGuest();
        facade.create(guest);

        given()
                .when()
                .get("/guest/1")
                .then()
                .assertThat()
                .body("name", equalTo("TestGuest"));
    }

    @Test
    public void testAll() {
        Guest guest = ce.createTestGuest();
        facade.create(guest);
        facade.create(guest);

        given()
                .when()
                .get("/guest/all")
                .then()
                .assertThat()
                .body("size()", is(2));
    }

    @Test
    public void testGetGuestShowList() throws NotFoundException {
        Guest guest = ce.createTestGuest();
        Guest createdGuest = facade.create(guest);

        ShowFacade sf = ShowFacade.getFacade(emf);
        Show rs1 = sf.create(ce.createTestShow());
        Show rs2 = sf.create(ce.createTestShow());
        rs1.addGuest(createdGuest);
        rs2.addGuest(createdGuest);
        sf.update(rs1);
        sf.update(rs2);

        given()
                .when()
                .get("/guest/1/shows")
                .then()
                .assertThat()
                .body("size()", is(2));
    }

    @Test
    public void testSignupShow() {
        Guest guest = ce.createTestGuest();
        facade.create(guest);

        ShowFacade sf = ShowFacade.getFacade(emf);
        sf.create(ce.createTestShow());

        given()
                .when()
                .get("/guest/1/show/1");
        expect().statusCode(200)
                .given()
                .when()
                .get("/guest/1/shows")
                .then()
                .assertThat()
                .body("size()", is(1));
    }

    @Test
    public void testSignupFestival() {
        Guest guest = ce.createTestGuest();
        facade.create(guest);

        FestivalFacade ff = FestivalFacade.getFacade(emf);
        ff.create(ce.createTestFestival());

        given()
                .when()
                .get("/guest/1/festival/1")
                .then()
                .assertThat()
                .body("festival.name", equalTo("TestFestival"));
    }
}