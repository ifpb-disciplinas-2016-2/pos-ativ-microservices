package ifpb.pos.hospedagem.entities;

import ifpb.pos.hospedagem.resources.HotelResources;
import java.net.URI;
import java.io.Serializable;
import java.time.LocalDate;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Natarajan
 */
//@XmlRootElement
public class LinkedReservaHotel implements Serializable {

    private Long id;
    
    private Link cliente;
    private Link hotel;
    
//    private LocalDate dataInicio;
//    private int quantidadeDiarias;

    public LinkedReservaHotel() {
    }
    
    public LinkedReservaHotel(ReservaHotel reservaHotel, UriInfo uriInfoHospedagem) {
        
        this.id = reservaHotel.getId();
//        this.dataInicio = reservaHotel.getDataInicio();
//        this.quantidadeDiarias = reservaHotel.getQuantidadeDiarias();
        if (reservaHotel.getHotel() != null) 
            buildLinkHotel(reservaHotel.getHotel(), uriInfoHospedagem);
        if (reservaHotel.getIdCliente() != null) {
            buildLinkCliente(reservaHotel.getIdCliente());
        }
        
    }

    private void buildLinkHotel(Hotel hotel, UriInfo uriInfoHospedagem) {
        
        String idHotel = hotel.getId().toString();
        
        URI uri = uriInfoHospedagem.getBaseUriBuilder()
                .path(HotelResources.class)
                .path(idHotel)
                .build();
        
        Link hotelLink = new Link("hotel", uri.toString());
        this.hotel = hotelLink;
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Link getCliente() {
        return cliente;
    }

    public void setCliente(Link cliente) {
        this.cliente = cliente;
    }

    public Link getHotel() {
        return hotel;
    }

    public void setHotel(Link hotel) {
        this.hotel = hotel;
    }

    
    @Override
    public String toString() {
        return "LinkedReservaHotel{" + "id=" + id + ", cliente=" + cliente + ", hotel=" + hotel + '}';
    }

    private void buildLinkCliente(Long idCliente) {
        //validando o cliente
        Client cliente = ClientBuilder.newClient();
        WebTarget target = cliente.target("http://cliente-resource:8080/cliente-ws/api/cliente/valida/{id}")
                    .resolveTemplate("id", idCliente);
        Response resposta = target
                .request()
                .get();
        if (resposta.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
            this.cliente = resposta.readEntity(Link.class);
        } 
        
    }
    
    
    
}
