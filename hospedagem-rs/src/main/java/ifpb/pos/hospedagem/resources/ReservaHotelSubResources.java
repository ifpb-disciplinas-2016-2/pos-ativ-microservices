/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pos.hospedagem.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ifpb.pos.hospedagem.entities.Hotel;
import ifpb.pos.hospedagem.entities.Link;
import ifpb.pos.hospedagem.entities.LinkedReservaHotel;
import ifpb.pos.hospedagem.entities.ReservaHotel;
import ifpb.pos.hospedagem.services.HotelService;
import ifpb.pos.hospedagem.services.ReservaHotelService;
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
//@Path("/{idReserva}")
@Path("reserva-hotel")
@Stateless
public class ReservaHotelSubResources {
    
    @Context 
    private UriInfo info;
    
    @EJB
    private ReservaHotelService reservaService;
    
    @EJB
    private HotelService hotelService;

    public ReservaHotelSubResources() {
    }
    
    @PUT
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response addHotelEmReserva(
            @PathParam("idReserva") String idReserva, 
            @PathParam("idHotel") String idHotel,
            ReservaHotel reservaEnviada) throws JsonProcessingException{
        
        System.out.println("ENTROU NO MÉTODO add hotel no sub recurso******");
        
        ReservaHotel reserva = null;
        
        if (reservaEnviada != null) {
            reserva = reservaService.buscar(reservaEnviada.getId());
        } else {
            reserva = reservaService.buscar(new Long(idReserva));
        }
        
//        ReservaHotel reserva = reservaService.buscar(new Long(idReserva));
        Hotel hotel = hotelService.buscar(new Long(idHotel));
        
        if (reserva == null || hotel == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        reserva.setHotel(hotel);
        
        reserva = reservaService.atualizar(reserva);
        
        LinkedReservaHotel reservaLink = new LinkedReservaHotel(reserva, info);
        String result = new ObjectMapper().writeValueAsString(reservaLink);
        
        return Response
                .ok()
                .entity(result)
                .build();
        
    }
            
    
    
}
