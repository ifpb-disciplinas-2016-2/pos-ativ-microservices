///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ifpb.pos.agencia.rs.resources;
//
//import ifpb.pos.agencia.rs.entities.ErrorMessage;
//import ifpb.pos.agencia.rs.entities.LinkedPacote;
//import ifpb.pos.agencia.rs.entities.Pacote;
//import ifpb.pos.agencia.rs.services.PacoteService;
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//
///**
// *
// * @author natarajan
// */
////@Path("/{idHotel}")
//@Path("/")
////@Path("reserva-hotel")
//@Stateless
//public class PacoteHotelSubresources {
//
//    private static String messageError = "Não é possível solicitar uma reserva no pacote sem informar antes o cliente.";
//    private final Client client = ClientBuilder.newClient();
//    private final WebTarget hotelWebtarget = client.target("http://hospedagem-resource:8080/hospedagem-rs/api");
//    
//    
//    @Context 
//    private UriInfo info;
//    
//    @EJB
//    private PacoteService pacoteService;
//
//    
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response addReservaHotelEmPacote(
//            @PathParam("idPacote") String idPacote, 
//            @PathParam("idHotel") String idHotel
//            ){
//        
//        
//        Pacote pacote = pacoteService.buscar(new Long(idPacote));
//        
//        if (pacote.getIdCliente() == null) {
//            return Response
//                    .status(Response.Status.FORBIDDEN)
//                    .entity(new ErrorMessage(messageError))
//                    .build();
//        }
//        
//        Response validaHotel = validaHotel(idHotel);
//        
//        System.out.println("chegou a resposta sobre hotel");
//        if (pacote != null && validaHotel.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
//            //cria uma reserva para o hotel indicado
//            Response respostaCriarReserva = criarReserva(idHotel);
//            
//            ReservaHotel reservaHotel = respostaCriarReserva.readEntity(ReservaHotel.class);
//            
//            setClienteNaReserva(reservaHotel.getId(), pacote.getIdCliente());
//            
//            Response responseReserva = setHotelNaReserva(reservaHotel.getId(), idHotel);
//            
//            //retornar pacote atualizado
//            pacote.setReservaHotel(reservaHotel.getId());
//            Pacote pacoteAtualizado = pacoteService.atualizar(pacote);
//            LinkedPacote pacoteLinked = new LinkedPacote(pacoteAtualizado);
//            return Response
//                    .ok()
//                    .entity(pacoteLinked)
//                    .build();
//        } else {
//            return Response.status(Response.Status.NO_CONTENT).build();
//        }
//    }
//    
//    private Response validaCliente(Long idCliente){
//        //validando o cliente
//        Client cliente = ClientBuilder.newClient();
//        WebTarget target = cliente.target("http://cliente-resource:8080/cliente-rs/api/cliente/valida/{id}")
//                    .resolveTemplate("id", idCliente);
//        
//        return target
//                .request()
//                .get();
//    }
//
//    private Response validaHotel(String idHotel) {
//        
//        return hotelWebtarget.path("valida/{id}")
//                .resolveTemplate("id", idHotel)
//                .request()
//                .get();
//    }
//    
//    private Response criarReserva(String idHotel){
//
//        return hotelWebtarget.path("reserva")
//                .request()
//                .post(null);
//        
//    }
//
//    private void setClienteNaReserva(Long idReserva, Long idCliente) {
//
//        
//        ReservaHotel reserva = new ReservaHotel();
//        reserva.setId(idReserva);
//        
//        Response r = hotelWebtarget.path("reserva/{idReserva}/cliente/{idCliente}")
//                .resolveTemplate("idReserva", idReserva)
//                .resolveTemplate("idCliente", idCliente)
//                .request()
//                .put(Entity.json(reserva));
//    }
//
//    private Response setHotelNaReserva(Long id, String idHotel) {
//                
//        ReservaHotel reserva = new ReservaHotel();
//        reserva.setId(id);
//        
//        return hotelWebtarget
//                .path("reserva/{idReserva}/hotel/{idHotel}")
//                .resolveTemplate("idReserva", id.toString())
//                .resolveTemplate("idHotel", idHotel)
//                .request()
//                .put(Entity.json(reserva));
//        
//    }
//    
//    
//}
