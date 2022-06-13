package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.FestivalDTO;
import entities.Festival;
import errorhandling.NotFoundException;
import facades.FestivalFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("festival")
public class FestivalResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final FestivalFacade FACADE =  FestivalFacade.getFacade(EMF);
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
        FestivalDTO dto = GSON.fromJson(jsonContext, FestivalDTO.class);
        Festival festival = new Festival(dto);
        FestivalDTO created = new FestivalDTO(FACADE.create(festival));
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
        FestivalDTO dto = GSON.fromJson(jsonContext, FestivalDTO.class);
        Festival festival = new Festival(dto);
        festival.setId(id);
        FestivalDTO updated = new FestivalDTO(FACADE.update(festival));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(updated))
                .build();
    }

    @Path("{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id) throws NotFoundException {
        FestivalDTO deleted = new FestivalDTO(FACADE.delete(id));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(deleted))
                .build();
    }

    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") Long id) throws NotFoundException {
        FestivalDTO found = new FestivalDTO(FACADE.getById(id));
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(found))
                .build();
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        List<FestivalDTO> dtoList = new ArrayList<>();
        for (Festival festival : FACADE.getAll()) {
            dtoList.add(new FestivalDTO(festival));
        }
        return Response
                .ok("SUCCESS")
                .entity(GSON.toJson(dtoList))
                .build();
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getFestivalCount() {
        long count = FACADE.getCount();
        return "{\"count\":"+count+"}";
    }
}
