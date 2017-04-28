package ifpb.pos.agencia.rs.resources;

import com.google.gson.Gson;
import ifpb.pos.agencia.rs.entities.ErrorMessage;
import ifpb.pos.agencia.rs.entities.Link;
import ifpb.pos.agencia.rs.entities.LinkedPacote;
import ifpb.pos.agencia.rs.entities.Pacote;
import ifpb.pos.agencia.rs.entities.ReservaHotel;
import ifpb.pos.agencia.rs.services.PacoteService;
//import ifpb.pos.hospedagem.entities.ReservaHotel;
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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.json.JSONObject;


/**
 * @author natarajan
 */
@Path("pacote")
@Stateless
public class PacoteResources {

    @Context
    private ResourceContext resourceContext;
    
    @EJB
    private PacoteService pacoteService;

    private static String messageError = "Não é possível solicitar uma reserva no pacote sem informar antes o cliente.";
    
    private final Client client = ClientBuilder.newClient();
    private final WebTarget hotelWebtarget = client.target("http://hospedagem-resource:8080/hospedagem-rs/api");
    private final WebTarget passagemWebtarget = client.target("http://passagem-resource:8080/passagem-rs/api");
    private final WebTarget clienteWebtarget = client.target("http://cliente-resource:8080/cliente-rs/api");
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPacotes() {
        List<Pacote> listar = pacoteService.listar();
        GenericEntity<List<Pacote>> retorno
                = new GenericEntity<List<Pacote>>(listar) {
        };
        return Response.ok(retorno).build();
    }
    
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    public Response addPacote(@Context UriInfo uriInfo) throws URISyntaxException {

        Pacote pacote = new Pacote();
                
        Pacote saved = pacoteService.salvar(pacote);

        return Response
                .created(new URI("/agencia-rs/api/pacote/" + pacote.getId()))
                .entity(pacote)
                .build();

    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPacote(@PathParam("id") Long id) {
        Pacote pacote = pacoteService.buscar(id);
        if (pacote == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        LinkedPacote pack = new LinkedPacote(pacote);
        
        return Response.ok().entity(pack).build();
    }
    

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePacote(@PathParam("id") Long id) {

        Pacote pacoteRetorno = pacoteService.buscar(id);
        if (pacoteRetorno == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        Pacote pacoteRemovido = pacoteService.remover(id);
        
        return Response
                .ok()
                .entity(pacoteRemovido)
                .build();
    }
    
    

//    @Path("/{idPacote}/cliente/{idCliente}")
//    public PacoteHotelSubresources addCliente(){
//        System.out.println("chamando sub recurso para add cliente");
//        return this.resourceContext.getResource(PacoteHotelSubresources.class);
//    }
    

//    @Path("/{idPacote}/hotel/{idHotel}")
//    public PacoteClienteSubResources addReservaHotel(){
//        System.out.println("Chamando sub recurso para add HOTEL");
//        return this.resourceContext.getResource(PacoteClienteSubResources.class);
//    }

    
    @PUT
    @Path("/{idPacote}/cliente/{idCliente}")
    @Consumes(value = MediaType.APPLICATION_XML)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response addClienteEmReserva(
            @PathParam("idPacote") String idPacote, 
            @PathParam("idCliente") String idCliente
            ){
             
        Pacote pacote = pacoteService.buscar(new Long(idPacote));
        
        //validando o cliente
//        Client cliente = ClientBuilder.newClient();
//        WebTarget target = cliente.target("http://cliente-resource:8080/cliente-rs/api/cliente/valida/{id}")
//                    .resolveTemplate("id", idCliente);
        
        Response resposta = validaCliente(new Long(idCliente));
        
        System.out.println("chegou a resposta sobre cliente");
        if (pacote != null && resposta.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
            pacote.setIdCliente(new Long(idCliente));
            Pacote pacoteAtualizado = pacoteService.atualizar(pacote);
            LinkedPacote pacoteLinked = new LinkedPacote(pacoteAtualizado);
            return Response
                    .ok()
                    .entity(pacoteLinked)
                    .build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }
    
    
    @PUT
    @Path("/{idPacote}/hotel/{idHotel}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReservaHotelEmPacote(
            @PathParam("idPacote") String idPacote, 
            @PathParam("idHotel") String idHotel
            ){
        Pacote pacote = pacoteService.buscar(new Long(idPacote));
        
        if (pacote.getIdCliente() == null) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(new ErrorMessage(messageError))
                    .build();
        }
        
        Response validaHotel = validaHotel(idHotel);
        
        if (pacote != null && validaHotel.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
            //cria uma reserva para o hotel indicado
            
//            JsonObject reservaJSON = criarReservaHotel(idHotel).readEntity(JsonObject.class);            
//            String jsonIdReserva = getJsonElement(reservaJSON, "id");
            
            Response respostaCriarReserva = criarReservaHotel(idHotel);    
            ReservaHotel reservaHotel = respostaCriarReserva.readEntity(ReservaHotel.class);
            
            setClienteNaReserva(reservaHotel.getId(), pacote.getIdCliente());
            Response responseReserva = setHotelNaReserva(reservaHotel.getId(), idHotel);
            
            pacote.setReservaHotel(reservaHotel.getId());
            Pacote pacoteAtualizado = pacoteService.atualizar(pacote);
            LinkedPacote pacoteLinked = new LinkedPacote(pacoteAtualizado);
            return Response
                    .ok()
                    .entity(pacoteLinked)
                    .build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }
    
    @PUT
    @Path("/{idPacote}/passagem/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReservaPassagem(
            @PathParam("idPacote") String idPacote, 
            @PathParam("idEmpresa") String idEmpresa
            ){
        Pacote pacote = pacoteService.buscar(new Long(idPacote));
        
        if (pacote.getIdCliente() == null) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(new ErrorMessage(messageError))
                    .build();
        }
        
        Response validaEmpresa = validaEmpresa(idEmpresa);
        
        if (pacote != null && validaEmpresa.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
            //cria uma reserva para o hotel indicado
            Response respostaCriarReserva = criarReservaPassagem();    
            String reservaJSON = respostaCriarReserva.readEntity(String.class);
            
            System.out.println("JSON RECEBIDO: " + reservaJSON.toString());

            JSONObject jsonObj = new JSONObject(reservaJSON);
            Long idReserva = (Long) jsonObj.getLong("id");
            
            setClienteNaReservaPassagem(idReserva, pacote.getIdCliente());
            Response responseReserva = setEmpresaNaReserva(idReserva, idEmpresa);
            
            pacote.setReservaPassagem(idReserva);
            
            Pacote pacoteAtualizado = pacoteService.atualizar(pacote);
            LinkedPacote pacoteLinked = new LinkedPacote(pacoteAtualizado);
            return Response
                    .ok()
                    .entity(pacoteLinked)
                    .build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }
    
    private Response validaCliente(Long idCliente){
        //validando o cliente
        return clienteWebtarget
                .resolveTemplate("id", idCliente)
                .request().get();
    }

    private Response validaHotel(String idHotel) {
        return hotelWebtarget.path("valida/{id}")
                .resolveTemplate("id", idHotel).request().get();
    }
    
    private Response criarReservaHotel(String idHotel){
        return hotelWebtarget.path("reserva").request().post(null);
    }

    private void setClienteNaReserva(Long idReserva, Long idCliente) {        
        ReservaHotel reserva = new ReservaHotel();
        reserva.setId(idReserva);
        Response r = hotelWebtarget.path("reserva/{idReserva}/cliente/{idCliente}")
                .resolveTemplate("idReserva", idReserva)
                .resolveTemplate("idCliente", idCliente)
                .request()
                .put(Entity.json(reserva));
    }

    private Response setHotelNaReserva(Long id, String idHotel) {        
        ReservaHotel reserva = new ReservaHotel();
        reserva.setId(id);
        return hotelWebtarget
                .path("reserva/{idReserva}/hotel/{idHotel}")
                .resolveTemplate("idReserva", id.toString())
                .resolveTemplate("idHotel", idHotel)
                .request()
                .put(Entity.json(reserva));
    }

    private Response criarReservaPassagem() {
        return passagemWebtarget.path("reserva").request().post(null);
    }

    private Response validaEmpresa(String idEmpresa) {
        return passagemWebtarget.path("valida/{id}")
                .resolveTemplate("id", idEmpresa).request().get();
    }

    private void setClienteNaReservaPassagem(Long idReserva, Long idCliente) {
        Response r = passagemWebtarget.path("reserva/{idReserva}/cliente/{idCliente}")
                .resolveTemplate("idReserva", idReserva)
                .resolveTemplate("idCliente", idCliente)
                .request()
                .put(Entity.json(new Link()));
    }

    private Response setEmpresaNaReserva(Long idReserva, String idEmpresa) {
        return passagemWebtarget
                .path("reserva/{idReserva}/empresa/{idEmpresa}")
                .resolveTemplate("idReserva", idReserva)
                .resolveTemplate("idEmpresa", idEmpresa)
                .request()
                .put(Entity.json(new Link()));
    }
    
    public String getJsonElement(String json, String element){
        
//        json.get(json)
//        System.out.println("Objeto JSON: " + json.toString());
//        Gson gson = new Gson();
//        return gson.fromJson(json, JSONObject.class).get(element).getAsString();
        return null;
    }
    
}
