package ifpb.pos.hospedagem.resources;

import ifpb.pos.hospedagem.entities.Hotel;
import ifpb.pos.hospedagem.entities.LinkedReservaHotel;
import ifpb.pos.hospedagem.entities.ReservaHotel;
import ifpb.pos.hospedagem.services.HotelService;
import ifpb.pos.hospedagem.services.ReservaHotelService;
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
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author natarajan
 */
@Path("/reserva")
@Stateless
public class ReservaResources {
    
    @Context
    private ResourceContext resourceContext;

    @EJB
    private ReservaHotelService reservaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarReservas() {
        List<Hotel> listar = reservaService.listar();
        GenericEntity<List<Hotel>> retorno
                = new GenericEntity<List<Hotel>>(listar) {
        };
        return Response.ok(retorno).build();
    }
    
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    public Response addReserva(Hotel hotel, 
            @Context UriInfo uriInfo) throws URISyntaxException {

        ReservaHotel reserva = new ReservaHotel();
        reservaService.salvar(reserva);

        String id = String.valueOf(reserva.getId());
        URI uriOrder = uriInfo.getBaseUriBuilder() // .../ws/
                .path(ReservaResources.class) // .../ws/reserva
                .path(id) // .../ws/reserva/1
                .build();

        return Response
                .created(uriOrder)
                .entity(reserva)
                .build();
    }

    
    @Path("{idReserva}/hotel/{idHotel}")
    public ReservaHotelSubResources addHotel() {
        System.out.println("vai pro sub resource reserva-hotel *******");
        return this.resourceContext
                .getResource(ReservaHotelSubResources.class);
    }
  
    @Path("{idReserva}/cliente/{idCliente}")
    public ReservaClienteSubResources addClient() {
        System.out.println("vai pro sub resource reserva-cliente *******");
        return this.resourceContext
                .getResource(ReservaClienteSubResources.class);
    }
    

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReserva(@PathParam("id") Long id, @Context UriInfo info) {
        ReservaHotel reserva = reservaService.buscar(id);
        if (reserva == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        LinkedReservaHotel reservaLink = new LinkedReservaHotel(reserva, info);
        System.out.println(reservaLink);
        
        return Response.ok().entity(reservaLink).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    public Response updateReservaHotel(@PathParam("id") Long id, ReservaHotel reservaAtualizada) throws URISyntaxException {

        reservaAtualizada.setId(id);
        ReservaHotel reservaRetornada = reservaService.atualizar(reservaAtualizada);
        if (reservaRetornada == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response
                .ok()
                .entity(reservaRetornada)
                .location(new URI("/hospedagem-ws/api/hotel/" + id))
                .build();

    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteHotel(@PathParam("id") Long id) {

        ReservaHotel reserva = reservaService.buscar(id);
        if (reserva == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        ReservaHotel reservaRemovida = reservaService.remover(id);
        
        return Response
                .ok()
                .entity(reservaRemovida)
                .build();
    }
    
    

}
