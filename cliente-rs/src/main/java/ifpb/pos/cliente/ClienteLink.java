package ifpb.pos.cliente;

import java.net.URI;
import javax.ws.rs.core.UriInfo;

/**
 * @author Natarajan
 */
public class ClienteLink {

    private Long id;
    private Link cliente;
    
    public ClienteLink() {
    }

    public ClienteLink(Cliente cliente, UriInfo info) {
        this.id = cliente.getId();
        buildClienteLink(cliente, info);
    }

    private void buildClienteLink(Cliente cliente, UriInfo info) {
        
//        System.out.println("absolute path: ::: " + info.getAbsolutePath());
//        URI uri = info.getBaseUriBuilder()
//                .path(ClienteResources.class)
//                .path(cliente.getId().toString())
//                .build();
        String uri = "http://localhost:8081/cliente-rs/api/cliente/" + cliente.getId();
        Link linkCliente = new Link("cliente", uri);
        this.cliente = linkCliente;
    }

    public Long getId() {
        return id;
    }

    public Link getCliente() {
        return cliente;
    }

    
}
