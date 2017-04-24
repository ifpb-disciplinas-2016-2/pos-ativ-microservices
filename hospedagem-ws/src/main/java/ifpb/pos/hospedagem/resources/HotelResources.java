package ifpb.pos.hospedagem.resources;

import ifpb.pos.hospedagem.entities.Hotel;
import ifpb.pos.hospedagem.services.HotelService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author natarajan
 */
@Path("hotel")
@Stateless
public class HotelResources {

    @EJB
    private HotelService hotelService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarHoteis() {
        List<Hotel> listar = hotelService.listar();
        GenericEntity<List<Hotel>> retorno
                = new GenericEntity<List<Hotel>>(listar) {
        };
        return Response.ok(retorno).build();
    }
    
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    public Response addHotel(Hotel hotel, 
            @Context UriInfo uriInfo) throws URISyntaxException {

        Hotel saved = hotelService.salvar(hotel);

        return Response
                .created(new URI("/hospedagem-ws/api/hotel/" + hotel.getId()))
                .entity(hotel)
                .build();

    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getHotel(@PathParam("id") Long id) {
        Hotel hotel = hotelService.buscar(id);
        if (hotel == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return Response.ok().entity(hotel).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    public Response updateHotel(@PathParam("id") Long id, Hotel hotelAtualizado) throws URISyntaxException {

        hotelAtualizado.setId(id);
        Hotel hotelRetorno = hotelService.atualizar(hotelAtualizado);
        if (hotelRetorno == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response
                .ok()
                .entity(hotelRetorno)
                .location(new URI("/hospedagem-ws/api/hotel/" + id))
                .build();

    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteHotel(@PathParam("id") Long id) {

        Hotel hotelRetorno = hotelService.buscar(id);
        if (hotelRetorno == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        Hotel hotelRemovido = hotelService.remover(id);
        
        return Response
                .ok()
                .entity(hotelRemovido)
                .build();
    }
    
    

}
