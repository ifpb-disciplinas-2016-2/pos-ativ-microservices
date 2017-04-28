package ifpb.pos.passagem.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ifpb.pos.passagem.entities.Empresa;
import ifpb.pos.passagem.entities.Link;
import ifpb.pos.passagem.entities.LinkedReservaPassagem;
import ifpb.pos.passagem.entities.ReservaPassagem;
import ifpb.pos.passagem.services.ReservaPassagemService;
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
    private ReservaPassagemService reservaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarReservas() {
        List<ReservaPassagem> listar = reservaService.listar();
        GenericEntity<List<ReservaPassagem>> retorno
                = new GenericEntity<List<ReservaPassagem>>(listar) {
        };
        return Response.ok(retorno).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReserva(Empresa empresa,
            @Context UriInfo uriInfo) throws URISyntaxException {

        ReservaPassagem reserva = new ReservaPassagem();
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


    @Path("{idReserva}/empresa/{idEmpresa}")
    public ReservaPassagemSubResources addEmpresa() {
        System.out.println("vai pro sub resource reserva-empresa *******");
        return this.resourceContext
                .getResource(ReservaPassagemSubResources.class);
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
    public Response getReserva(@PathParam("id") Long id, @Context UriInfo info) throws JsonProcessingException {
        ReservaPassagem reserva = reservaService.buscar(id);
        if (reserva == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        LinkedReservaPassagem reservaLink = new LinkedReservaPassagem(reserva, info);
        String result = new ObjectMapper().writeValueAsString(reservaLink);

        return Response.ok().entity(result).build();
    }

    @GET
    @Path("/valida/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validar(@PathParam("id") Long id, @Context UriInfo info) {
        ReservaPassagem reserva = reservaService.buscar(id);
        
        /* 
            assim como no exemplo da sala de aula
            vamos utilizar o código do Response
            para o tratamento de erro 
        */
        if(reserva==null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        
        String externalUrl = "http://localhost:8083/passagem-rs/api/reserva/"+ id ;
        
        Link link = new Link("reservaHotel", externalUrl);
        return Response.ok().entity(link).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    public Response updateReserva(@PathParam("id") Long id, ReservaPassagem reservaAtualizada) throws URISyntaxException {

        reservaAtualizada.setId(id);
        ReservaPassagem reservaRetornada = reservaService.atualizar(reservaAtualizada);
        if (reservaRetornada == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response
                .ok()
                .entity(reservaRetornada)
                .location(new URI("/passagem-rs/api/empresa/" + id))
                .build();

    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReserva(@PathParam("id") Long id) {

        ReservaPassagem reserva = reservaService.buscar(id);
        if (reserva == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ReservaPassagem reservaRemovida = reservaService.remover(id);

        return Response
                .ok()
                .entity(reservaRemovida)
                .build();
    }



}
