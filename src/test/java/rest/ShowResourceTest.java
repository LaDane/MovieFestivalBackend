package rest;

import Helpers.CreateEntity;
import Helpers.ResetDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ShowDTO;
import entities.Show;
import facades.ShowFacade;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Disabled
class ShowResourceTest {
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

    private static ShowFacade facade;
    private CreateEntity ce;


    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = ShowFacade.getFacade(emf);

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
        ResetDB.truncate(emf);
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
                .get("/show/ping")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreate() {
        Show show = ce.createTestShow();
        String requestBody = GSON.toJson(new ShowDTO(show));
        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/show/create")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("TestShow"));
    }

    @Test
    public void testUpdate() {
        Show show = ce.createTestShow();
        facade.create(show);

        show.setName("RestShow");
        String requestBody = GSON.toJson(new ShowDTO(show));

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put("/show/1")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("name", equalTo("RestShow"));
    }

    @Test
    public void testDelete() {
        Show show = ce.createTestShow();
        facade.create(show);

        given()
                .when()
                .delete("/show/1");
        expect().statusCode(200)
                .given()
                .when()
                .get("/show/count")
                .then()
                .assertThat()
                .body("count", equalTo(0));
    }

    @Test
    public void testGetById() {
        Show show = ce.createTestShow();
        facade.create(show);

        given()
                .when()
                .get("/show/1")
                .then()
                .assertThat()
                .body("name", equalTo("TestShow"));
    }

    @Test
    public void testAll() {
        Show show = ce.createTestShow();
        facade.create(show);
        facade.create(show);

        given()
                .when()
                .get("/show/all")
                .then()
                .assertThat()
                .body("size()", is(2));
    }
}