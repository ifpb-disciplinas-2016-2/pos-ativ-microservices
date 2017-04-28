package ifpb.pos.passagem.resources;

import ifpb.pos.passagem.entities.Empresa;
import ifpb.pos.passagem.services.EmpresaService;
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
import javax.ws.rs.core.UriInfo;

/**
 * @author natarajan
 */
@Path("empresa")
@Stateless
public class EmpresaResources {

    @EJB
    private EmpresaService empresaService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        List<Empresa> listar = empresaService.listar();
        GenericEntity<List<Empresa>> retorno
                = new GenericEntity<List<Empresa>>(listar) {
        };
        return Response.ok(retorno).build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    public Response addEmpresa(Empresa empresa,
            @Context UriInfo uriInfo) throws URISyntaxException {

        Empresa saved = empresaService.salvar(empresa);

        return Response
                .created(new URI("/passagem-rs/api/passagem/" + empresa.getId()))
                .entity(empresa)
                .build();

    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getEmpresa(@PathParam("id") Long id) {
        Empresa empresa = empresaService.buscar(id);
        if (empresa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok().entity(empresa).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
    public Response updateEmpresa(@PathParam("id") Long id, Empresa empresaAtualizado) throws URISyntaxException {

        empresaAtualizado.setId(id);
        Empresa empresaRetorno = empresaService.atualizar(empresaAtualizado);
        if (empresaRetorno == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response
                .ok()
                .entity(empresaRetorno)
                .location(new URI("/passagem-rs/api/empresa/" + id))
                .build();

    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmpresa(@PathParam("id") Long id) {

        Empresa empresaRetorno = empresaService.buscar(id);
        if (empresaRetorno == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Empresa empresaRemovido = empresaService.remover(id);

        return Response
                .ok()
                .entity(empresaRemovido)
                .build();
    }



}
