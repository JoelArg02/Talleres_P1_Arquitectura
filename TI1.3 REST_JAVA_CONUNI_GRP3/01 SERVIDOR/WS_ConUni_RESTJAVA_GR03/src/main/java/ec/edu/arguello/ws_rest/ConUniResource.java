package ec.edu.arguello.ws_rest;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.MediaType;
import ec.edu.arguello.models.ConUni;
import ec.edu.arguello.services.ConUniLongitudServicio;
import ec.edu.arguello.services.ConUniMasaServicio;
import ec.edu.arguello.services.ConUniTemperaturaServicio;
import jakarta.jws.WebParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;

@Path("ConUni")
@RequestScoped
public class ConUniResource {

    private final ConUniLongitudServicio longitudService = new ConUniLongitudServicio();
    private final ConUniMasaServicio masaService = new ConUniMasaServicio();
    private final ConUniTemperaturaServicio temperaturaService = new ConUniTemperaturaServicio();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ConUniResource
     */
    public ConUniResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ec.edu.yaranga.ws_rest.ConUniResource
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Path("convertUnit")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Double convertUnit(ConUni conUni) {
        String tipoLower = conUni.getType().toLowerCase();

        switch (tipoLower) {
            case "longitud":
            case "length":
                return longitudService.conversion(conUni);
            case "masa":
            case "mass":
                return masaService.conversion(conUni);
            case "temperatura":
            case "temperature":
                return temperaturaService.conversion(conUni);
            default:
                return 0.0; // Error: tipo no soportado
        }
    }

    /**
     *
     *
     * @param content representation for the resource
     */
    @GET
    @Path("supportedUnits/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String[] getSupportedUnits(@PathParam("type") String tipo) {
        String tipoLower = tipo.toLowerCase();

        switch (tipoLower) {
            case "longitud":
            case "length":
                return longitudService.getSupportedUnits();
            case "masa":
            case "mass":
                return masaService.getSupportedUnits();
            case "temperatura":
            case "temperature":
                return temperaturaService.getSupportedUnits();
            default:
                return new String[0];
        }
    }
    @GET
    @Path("supportedTypes")
    @Produces(MediaType.APPLICATION_JSON)
     public String[] getSupportedTypes() {
        return new String[]{"longitud", "masa", "temperatura"};
    }
}
