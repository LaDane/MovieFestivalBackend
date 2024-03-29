package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import errorhandling.NotFoundException;
import facades.FestivalFacade;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("user")
public class UserResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("usernames")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsernames() {
        List<String> allUsers = FACADE.getAllUsernames();
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(allUsers))
                .build();
    }

    @Path("users")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers() throws NotFoundException {
        JsonObject jo = FACADE.getAllUsers();
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(jo))
                .build();
    }
}
