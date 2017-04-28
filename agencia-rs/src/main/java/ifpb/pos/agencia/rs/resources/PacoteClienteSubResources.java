/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pos.agencia.rs.resources;


import ifpb.pos.agencia.rs.entities.LinkedPacote;
import ifpb.pos.agencia.rs.entities.Pacote;
import ifpb.pos.agencia.rs.services.PacoteService;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author natarajan
 */
//@Path("/{idCliente}")
@Path("/")
@Stateless
public class PacoteClienteSubResources {
    
    @Context 
    private UriInfo info;
    
    @EJB
    private PacoteService pacoteService;
    
    
    public PacoteClienteSubResources() {
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addClienteEmReserva(
            @PathParam("idPacote") String idPacote, 
            @PathParam("idCliente") String idCliente
            ){
             
        Pacote pacote = pacoteService.buscar(new Long(idPacote));
        
        //validando o cliente
        Client cliente = ClientBuilder.newClient();
        WebTarget target = cliente.target("http://cliente-resource:8080/cliente-rs/api/cliente/valida/{id}")
                    .resolveTemplate("id", idCliente);
        
        Response resposta = target
                .request()
                .get();
        
        System.out.println("chegou a resposta sobre cliente");
        if (pacote != null && resposta.getStatus() != Status.NO_CONTENT.getStatusCode()) {
            
//            Link clienteLink = resposta.readEntity(Link.class);
//            
            System.out.println("SETANDO CLIENTE");
            pacote.setIdCliente(new Long(idCliente));
            
            Pacote pacoteAtualizado = pacoteService.atualizar(pacote);
            
            System.out.println("PACOTE FINAL: " + pacote.getId());
            
            LinkedPacote pacoteLinked = new LinkedPacote(pacoteAtualizado);

            return Response
                    .ok()
                    .entity(pacoteLinked)
                    .build();
        } else {
            return Response.status(Status.NO_CONTENT).build();
        }
    }
    
    
}
