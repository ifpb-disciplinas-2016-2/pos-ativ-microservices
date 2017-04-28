/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pos.passagem.resources;

import ifpb.pos.passagem.entities.Empresa;
import ifpb.pos.passagem.entities.ReservaPassagem;
import ifpb.pos.passagem.services.EmpresaService;
import ifpb.pos.passagem.services.ReservaPassagemService;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author natarajan
 */
@Path("reserva-passagem")
@Stateless
public class ReservaPassagemSubResources {

    @Context
    private UriInfo info;

    @EJB
    private ReservaPassagemService reservaService;

    @EJB
    private EmpresaService empresaService;

    public ReservaPassagemSubResources() {
    }

    @PUT
    @Produces(value = MediaType.APPLICATION_XML)
    public Response addEmpresaEmReserva(
            @PathParam("idReserva") String idReserva,
            @PathParam("idEmpresa") String idEmpresa
            ){

        System.out.println("ENTROU NO MÉTODO add Empresa no sub recurso******");

        ReservaPassagem reserva = reservaService.buscar(new Long(idReserva));
        Empresa empresa = empresaService.buscar(new Long(idEmpresa));
        reserva.setEmpresa(empresa);

        reserva = reservaService.atualizar(reserva);

        return Response
                .ok()
                .entity(reserva)
                .build();

    }



}
