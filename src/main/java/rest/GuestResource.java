package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.GuestDTO;
import dtos.ShowDTO;
import entities.Guest;
import entities.Show;
import errorhandling.NotFoundException;
import facades.FestivalFacade;
import facades.GuestFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("guest")
public class GuestResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final GuestFacade FACADE =  GuestFacade.getFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("ping")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("create")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String jsonContext) {
        GuestDTO dto = GSON.fromJson(jsonContext, GuestDTO.class);
        Guest guest = new Guest(dto);
        GuestDTO created = new GuestDTO(FACADE.create(guest));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(created))
                .build();
    }

    @Path("create/{username}")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createWithUser(String jsonContext, @PathParam("username") String username) throws NotFoundException {
        GuestDTO dto = GSON.fromJson(jsonContext, GuestDTO.class);
        Guest guest = new Guest(dto);
        GuestDTO created = new GuestDTO(FACADE.create(guest, username));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(created))
                .build();
    }

    @Path("{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, String jsonContext) throws NotFoundException {
        GuestDTO dto = GSON.fromJson(jsonContext, GuestDTO.class);
        Guest guest = new Guest(dto);
        guest.setId(id);
        GuestDTO updated = new GuestDTO(FACADE.update(guest));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(updated))
                .build();
    }

    @Path("{id}/festival/{festivalId}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateWithFestival(@PathParam("id") Long id, @PathParam("festivalId") Long festivalId, String jsonContext) throws NotFoundException {
        GuestDTO dto = GSON.fromJson(jsonContext, GuestDTO.class);
        Guest guest = new Guest(dto);
        guest.setId(id);
        guest.setFestival(FestivalFacade.getFacade(EMF).getById(festivalId));
        GuestDTO updated = new GuestDTO(FACADE.update(guest));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(updated))
                .build();
    }

    @Path("{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id) throws NotFoundException {
        GuestDTO deleted = new GuestDTO(FACADE.delete(id));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(deleted))
                .build();
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") Long id) throws NotFoundException {
        GuestDTO found = new GuestDTO(FACADE.getById(id));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(found))
                .build();
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<GuestDTO> dtoList = new ArrayList<>();
        for (Guest guest : FACADE.getAll()) {
            dtoList.add(new GuestDTO(guest));
        }
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(dtoList))
                .build();
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getGuestCount() {
        long count = FACADE.getCount();
        return "{\"count\":"+count+"}";
    }

    @Path("{id}/shows")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getGuestShowList(@PathParam("id") Long id) {
        List<ShowDTO> dtoList = new ArrayList<>();
        for (Show s : FACADE.getGuestShowList(id)) {
            dtoList.add(new ShowDTO(s));
        }
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(dtoList))
                .build();
    }

    @Path("{guestId}/show/{showId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response signupShow(@PathParam("guestId") Long guestId, @PathParam("showId") Long showId) throws NotFoundException {
        GuestDTO g = new GuestDTO(FACADE.signupShow(guestId, showId));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(g))
                .build();
    }

    @Path("{guestId}/festival/{festivalId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response signupFestival(@PathParam("guestId") Long guestId, @PathParam("festivalId") Long festivalId) throws NotFoundException {
        GuestDTO g = new GuestDTO(FACADE.signupFestival(guestId, festivalId));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(g))
                .build();
    }

    @Path("checkguest/{username}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response checkGuestProfile(@PathParam("username") String username) throws NotFoundException {
        Guest guest = FACADE.checkGuestProfile(username);
        if (guest == null) {
            return Response.ok().build();
        }
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(new GuestDTO(guest)))
                .build();
    }
}
