/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pos.hospedagem.resources;

import ifpb.pos.hospedagem.entities.Hotel;
import ifpb.pos.hospedagem.entities.Link;
import ifpb.pos.hospedagem.entities.LinkedReservaHotel;
import ifpb.pos.hospedagem.entities.ReservaHotel;
import ifpb.pos.hospedagem.services.HotelService;
import ifpb.pos.hospedagem.services.ReservaHotelService;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
@Path("reserva-cliente")
@Stateless
public class ReservaClienteSubResources {
    
    @Context 
    private UriInfo info;
    
    @EJB
    private ReservaHotelService reservaService;
    
    
    public ReservaClienteSubResources() {
    }
    
    
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addClienteEmReserva(
            @PathParam("idReserva") String idReserva, 
            @PathParam("idCliente") String idCliente
            ){
        
        
        System.out.println("ENTROU NO MÉTODO add cliente no sub recurso******");
        
        
        ReservaHotel reserva = reservaService.buscar(new Long(idReserva));
        System.out.println("encontrou a reserva");
        
        //validando o cliente
        Client cliente = ClientBuilder.newClient();
        WebTarget target = cliente.target("http://cliente-resource:8080/cliente-ws/api/cliente/valida/{id}")
                    .resolveTemplate("id", idCliente);
        
        Response resposta = target
                .request()
                .get();
        
        System.out.println("chegou a resposta sobre cliente");
        if (resposta.getStatus() != Status.NO_CONTENT.getStatusCode()) {
            
            Link clienteLink = resposta.readEntity(Link.class);
            
            reserva.setIdCliente(new Long(idCliente));
            reserva = reservaService.atualizar(reserva);
            
            LinkedReservaHotel reservaLinked = new LinkedReservaHotel(reserva, info);
            reservaLinked.setCliente(clienteLink);

            return Response
                    .ok()
                    .entity(reservaLinked)
                    .build();
        } else {
            return Response.status(Status.NO_CONTENT).build();
        }
    }
    
    
}
