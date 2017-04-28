package ifpb.pos.agencia.rs.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;


/**
 * @author Natarajan
 */
//@XmlRootElement
@JsonPropertyOrder(value = {"id"})
public class LinkedPacote implements Serializable {

    private Long id;
    
    private Link cliente;
    private Link reservaHotel;
    private Link reservaPassagem;
    

    public LinkedPacote() {
    }
    
    public LinkedPacote(Pacote pacote) {
        this.id = pacote.getId();
        System.out.println("pegando cliente");
        if (pacote.getIdCliente() != null) buildLinkCliente(pacote.getIdCliente());
        
        System.out.println("pegando hotel");
        if (pacote.getReservaHotel() != null) buildLinkReservaHotel(pacote.getReservaHotel());
        
        System.out.println("pegando passagem");
        if (pacote.getReservaPassagem() != null) buildLinkReservaPassagem(pacote.getReservaPassagem());
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

    public Link getReservaHotel() {
        return reservaHotel;
    }

    public void setReservaHotel(Link reservaHotel) {
        this.reservaHotel = reservaHotel;
    }

    public Link getReservaPassagem() {
        return reservaPassagem;
    }

    public void setReservaPassagem(Link reservaPassagem) {
        this.reservaPassagem = reservaPassagem;
    }
    
    private void buildLinkCliente(Long idCliente) {
        //validando o cliente
        Client cliente = ClientBuilder.newClient();
        WebTarget target = cliente.target("http://cliente-resource:8080/cliente-rs/api/cliente/valida/{id}")
                    .resolveTemplate("id", idCliente);
        Response resposta = target
                .request()
                .get();
        if (resposta.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
            this.cliente = resposta.readEntity(Link.class);
        } 
        
    }
    
    private void buildLinkReservaHotel(Long idReservaHotel) {
        //validando o cliente
        Client cliente = ClientBuilder.newClient();
        WebTarget target = cliente.target("http://hospedagem-resource:8080/hospedagem-rs/api/reserva/valida/{id}")
                    .resolveTemplate("id", idReservaHotel);
        Response resposta = target
                .request()
                .get();
        if (resposta.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
            this.reservaHotel = resposta.readEntity(Link.class);
        }     
    }
    
    private void buildLinkReservaPassagem(Long idReserva) {
        //validando o cliente
        Client cliente = ClientBuilder.newClient();
        WebTarget target = cliente.target("http://passagem-resource:8080/passagem-rs/api/reserva/valida/{id}")
                    .resolveTemplate("id", idReserva);
        Response resposta = target
                .request()
                .get();
        if (resposta.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
            this.reservaPassagem = resposta.readEntity(Link.class);
        }     
    }
    
    
    
}
