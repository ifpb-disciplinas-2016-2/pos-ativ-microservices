package ifpb.pos.cliente;

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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

/**
 * @author natarajan
 */
@Path("cliente")
@Stateless
public class ClienteResources {

    @EJB
    private ClienteService clienteService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarProdutos() {
        List<Cliente> listar = clienteService.listar();
        GenericEntity<List<Cliente>> retorno
                = new GenericEntity<List<Cliente>>(listar) {
        };
        return Response.ok(retorno).build();
    }
    
    @GET
    @Path("/valida/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validar(@PathParam("id") Long id, @Context UriInfo info) {
        Cliente cliente = clienteService.buscar(id);
        
        /* 
            assim como no exemplo da sala de aula
            vamos utilizar o código do Response
            para o tratamento de erro 
        */
        if(cliente==null){
            return Response.status(Status.NO_CONTENT).build();
        }
        
        Link link = new ClienteLink(cliente, info).getCliente();
        return Response.ok().entity(link).build();
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    public Response addClient(Cliente cliente, 
            @Context UriInfo uriInfo) throws URISyntaxException {

        Cliente saved = clienteService.salvar(cliente);

        return Response
                .created(new URI("/cliente-ws/api/cliente/" + cliente.getId()))
                .entity(cliente)
                .build();

    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getClient(@PathParam("id") Long id) {
        Cliente cliente = clienteService.buscar(id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return Response.ok().entity(cliente).build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    public Response updateClient(@PathParam("id") Long id, Cliente clienteAtualizado) throws URISyntaxException {

        clienteAtualizado.setId(id);
        Cliente clienteRetorno = clienteService.atualizar(clienteAtualizado);
        if (clienteRetorno == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response
                .ok()
                .entity(clienteRetorno)
                .location(new URI("/cliente-ws/api/cliente/" + id))
                .build();

    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteClient(@PathParam("id") Long id) {

        Cliente clienteRetorno = clienteService.buscar(id);
        if (clienteRetorno == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        Cliente clienteRemovido = clienteService.remover(id);
        
        return Response
                .ok()
                .entity(clienteRemovido)
                .build();
    }

}
