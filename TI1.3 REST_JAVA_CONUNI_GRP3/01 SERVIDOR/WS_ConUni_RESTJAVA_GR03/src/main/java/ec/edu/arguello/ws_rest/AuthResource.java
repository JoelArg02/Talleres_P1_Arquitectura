package ec.edu.arguello.ws_rest;

import ec.edu.arguello.models.User;
import ec.edu.arguello.services.LoginServicio;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Path;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.MediaType;

@Path("Auth")
@RequestScoped
public class AuthResource {

    private final LoginServicio service = new LoginServicio();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AuthResource
     */
    public AuthResource() {
    }

    /**
     * Retrieves representation of an instance of
     * ec.edu.yaranga.ws_rest.AuthResource
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean login(
            User user
    ) {
        return service.login(user);
    }

}
