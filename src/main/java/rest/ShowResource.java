package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ShowDTO;
import entities.Show;
import errorhandling.NotFoundException;
import facades.FestivalFacade;
import facades.ShowFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("show")
public class ShowResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final ShowFacade FACADE =  ShowFacade.getFacade(EMF);
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
        ShowDTO dto = GSON.fromJson(jsonContext, ShowDTO.class);
        Show show = new Show(dto);
        ShowDTO created = new ShowDTO(FACADE.create(show));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(created))
                .build();
    }

    @Path("create/festival/{festivalId}")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createWithFestival(@PathParam("festivalId") Long festivalId, String jsonContext) throws NotFoundException {
        ShowDTO dto = GSON.fromJson(jsonContext, ShowDTO.class);
        Show show = new Show(dto);
        show.setFestival(FestivalFacade.getFacade(EMF).getById(festivalId));
        ShowDTO created = new ShowDTO(FACADE.create(show));
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
        ShowDTO dto = GSON.fromJson(jsonContext, ShowDTO.class);
        Show show = new Show(dto);
        show.setId(id);
        ShowDTO updated = new ShowDTO(FACADE.update(show));
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
        ShowDTO dto = GSON.fromJson(jsonContext, ShowDTO.class);
        Show show = new Show(dto);
        show.setId(id);
        show.setFestival(FestivalFacade.getFacade(EMF).getById(festivalId));
        ShowDTO updated = new ShowDTO(FACADE.update(show));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(updated))
                .build();
    }

    @Path("{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id) throws NotFoundException {
        ShowDTO deleted = new ShowDTO(FACADE.delete(id));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(deleted))
                .build();
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") Long id) throws NotFoundException {
        ShowDTO found = new ShowDTO(FACADE.getById(id));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(found))
                .build();
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<ShowDTO> dtoList = new ArrayList<>();
        for (Show show : FACADE.getAll()) {
            dtoList.add(new ShowDTO(show));
        }
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(dtoList))
                .build();
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getShowCount() {
        long count = FACADE.getCount();
        return "{\"count\":"+count+"}";
    }
}
