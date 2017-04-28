package ifpb.pos.passagem.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ifpb.pos.passagem.resources.EmpresaResources;
import java.net.URI;
import java.io.Serializable;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


/**
 * @author Natarajan
 */
//@XmlRootElement
@JsonPropertyOrder(value = {"id"})
public class LinkedReservaPassagem implements Serializable {

    private Long id;

    private Link cliente;
    private Link empresa;

//    private LocalDate dataInicio;
//    private int quantidadeDiarias;

    public LinkedReservaPassagem() {
    }

    public LinkedReservaPassagem(ReservaPassagem reservaPassagem, UriInfo uriInfoPassagem) {

        this.id = reservaPassagem.getId();
        if (reservaPassagem.getEmpresa()!= null)
            buildLinkEmpresa(reservaPassagem.getEmpresa(), uriInfoPassagem);
        if (reservaPassagem.getIdCliente() != null) {
            buildLinkCliente(reservaPassagem.getIdCliente());
        }

    }

    private void buildLinkEmpresa(Empresa empresa, UriInfo uriInfoPassagem) {

        String idEmpresa = empresa.getId().toString();

        URI uri = uriInfoPassagem.getBaseUriBuilder()
                .path(EmpresaResources.class)
                .path(idEmpresa)
                .build();

        Link empresaLink = new Link("empresa", uri.toString());
        this.empresa = empresaLink;

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

    public Link getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Link empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return "LinkedReservaPassagem{" + "id=" + id + ", cliente=" + cliente + ", empresa=" + empresa + '}';
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



}
