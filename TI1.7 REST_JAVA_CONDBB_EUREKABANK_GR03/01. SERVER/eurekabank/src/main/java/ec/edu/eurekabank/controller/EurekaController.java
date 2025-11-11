package ec.edu.eurekabank.controller;

import ec.edu.eurekabank.model.Movimiento;
import ec.edu.eurekabank.model.Cuenta;
import ec.edu.eurekabank.service.EurekaService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/eureka")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EurekaController {

    private final EurekaService service = new EurekaService();

    @GET
    @Path("/movimientos/{cuenta}")
    public Response traerMovimientos(@PathParam("cuenta") String cuenta) {
        try {
            List<Movimiento> lista = service.leerMovimientos(cuenta);
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al obtener movimientos.").build();
        }
    }

    @POST
    @Path("/deposito")
    public Response regDeposito(@QueryParam("cuenta") String cuenta,
                                @QueryParam("importe") double importe) {
        try {
            service.registrarDeposito(cuenta, importe, "0001");
            return Response.ok().entity("Depósito registrado con éxito.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al registrar el depósito.").build();
        }
    }

    @POST
    @Path("/retiro")
    public Response regRetiro(@QueryParam("cuenta") String cuenta,
                              @QueryParam("importe") double importe) {
        try {
            service.registrarRetiro(cuenta, importe, "0001");
            return Response.ok().entity("Retiro registrado con éxito.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al registrar el retiro.").build();
        }
    }

    @POST
    @Path("/transferencia")
    public Response regTransferencia(@QueryParam("cuentaOrigen") String cuentaOrigen,
                                     @QueryParam("cuentaDestino") String cuentaDestino,
                                     @QueryParam("importe") double importe) {
        try {
            service.registrarTransferencia(cuentaOrigen, cuentaDestino, importe, "0001");
            return Response.ok().entity("Transferencia registrada con éxito.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al registrar la transferencia.").build();
        }
    }

    @POST
    @Path("/login")
    public Response login(@QueryParam("username") String username,
                          @QueryParam("password") String password) {
        boolean ok = service.login(username, password);
        if (ok) {
            return Response.ok().entity(true).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity(false).build();
        }
    }

    @GET
    @Path("/balances")
    public Response traerBalances() {
        try {
            List<Cuenta> lista = service.leerBalances();
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error al obtener balances.").build();
        }
    }
}
