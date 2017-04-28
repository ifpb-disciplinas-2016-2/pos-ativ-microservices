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
////import ifpb.pos.hospedagem.entities.ReservaHotel;
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.container.ResourceContext;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
///**
// *
// * @author natarajan
// */
//@Path("/")
//@Stateless
//public class PacoteSubResources {
//    
//    private static String messageError = "Não é possível solicitar uma reserva no pacote sem informar antes o cliente.";
//    private final Client client = ClientBuilder.newClient();
//    private final WebTarget hotelWebtarget = client.target("http://hospedagem-resource:8080/hospedagem-rs/api");
//    
//    @Context
//    private ResourceContext resourceContext;
//    
//    
//    @EJB
//    private PacoteService pacoteService;
//    
//
//    @PUT
//    @Consumes(MediaType.APPLICATION_XML)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response addClienteEmReserva(
//            @PathParam("idPacote") String idPacote, 
//            @PathParam("idCliente") String idCliente
//            ){
//             
//        Pacote pacote = pacoteService.buscar(new Long(idPacote));
//        
//        //validando o cliente
//        Client cliente = ClientBuilder.newClient();
//        WebTarget target = cliente.target("http://cliente-resource:8080/cliente-rs/api/cliente/valida/{id}")
//                    .resolveTemplate("id", idCliente);
//        
//        Response resposta = target
//                .request()
//                .get();
//        
//        System.out.println("chegou a resposta sobre cliente");
//        if (pacote != null && resposta.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
//            
////            Link clienteLink = resposta.readEntity(Link.class);
////            
//            System.out.println("SETANDO CLIENTE");
//            pacote.setIdCliente(new Long(idCliente));
//            
//            Pacote pacoteAtualizado = pacoteService.atualizar(pacote);
//            
//            System.out.println("PACOTE FINAL: " + pacote.getId());
//            
//            LinkedPacote pacoteLinked = new LinkedPacote(pacoteAtualizado);
//
//            return Response
//                    .ok()
//                    .entity(pacoteLinked)
//                    .build();
//        } else {
//            return Response.status(Response.Status.NO_CONTENT).build();
//        }
//    }
//    
//    
//
////    @Path("/hotel/{idHotel}")
////    public PacoteHotelSubResources addReservaHotel(){
////        System.out.println("Chamando sub recurso para add HOTEL");
////        return this.resourceContext.getResource(PacoteHotelSubResources.class);
////    }
//    
//    
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_XML)
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
//        System.out.println("chegou a resposta sobre cliente");
//        if (pacote != null && validaHotel.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
//            //cria uma reserva para o hotel indicado
//            Response respostaCriarReserva = criarReserva(idHotel);
//            ReservaHotel reservaHotel = respostaCriarReserva.readEntity(ReservaHotel.class);
//            System.out.println("Tá aqui o ID da reserva: " + reservaHotel.getId());
//        
//            
//            setClienteNaReserva(reservaHotel.getId(), pacote.getIdCliente());
//            Response responseReserva = setHotelNaReserva(reservaHotel.getId(), idHotel);
//            
//            //retornar pacote atualizado
//            pacote.setReservaHotel(reservaHotel.getId());
//            Pacote pacoteAtualizado = pacoteService.atualizar(pacote);
//            
//            LinkedPacote pacoteLinked = new LinkedPacote(pacoteAtualizado);
//
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
//        Response r = hotelWebtarget.path("reserva/{idReserva}/cliente/{idCliente}")
//                .resolveTemplate("idReserva", idReserva)
//                .resolveTemplate("idCliente", idCliente)
//                .request()
//                .put(null);
//    }
//
//    private Response setHotelNaReserva(Long id, String idHotel) {
//        return hotelWebtarget
//                .path("reserva/{idReserva}/hotel/{idHotel}")
//                .resolveTemplate("idReserva", id.toString())
//                .resolveTemplate("idCliente", idHotel)
//                .request()
//                .put(null);
//        
//    }
//    
//    
//}
