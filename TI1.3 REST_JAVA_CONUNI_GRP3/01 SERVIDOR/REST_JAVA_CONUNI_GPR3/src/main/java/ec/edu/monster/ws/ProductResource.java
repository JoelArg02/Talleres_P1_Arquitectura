package ec.edu.monster.ws;

import ec.edu.arguello.rest_java_conuni_gpr3.models.Products;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Path("product")
@RequestScoped
public class ProductResource {

    @Context
    private UriInfo context;

    public ProductResource() {}

    private static final List<Products> productos = new ArrayList<>();
    private static int nextId = 4; // Comienza después de los productos iniciales

    static {
        productos.add(new Products(1, "Laptop Lenovo", 950.00));
        productos.add(new Products(2, "Mouse Logitech", 25.50));
        productos.add(new Products(3, "Teclado Redragon", 45.99));
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        return "Servicio de productos activo";
    }

    @GET
    @Path("getXml")
    public String getXml() {
        return "perris";
    }

    @GET
    @Path("listar")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Products> listarProductos() {
        return productos;
    }

    // Nuevo: Buscar producto por ID
    @GET
    @Path("buscar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarProducto(@PathParam("id") int id) {
        for (Products p : productos) {
            if (p.getIdProduct() == id) {
                return Response.ok(p).build();
            }
        }
        JsonObject error = Json.createObjectBuilder()
                .add("error", "Producto con ID " + id + " no encontrado")
                .build();
        return Response.status(Response.Status.NOT_FOUND).entity(error.toString()).build();
    }

    // Guardar producto con ID auto-incremental
    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveProduct(String body) {
        try {
            if (body == null || body.isBlank()) {
                throw new IllegalArgumentException("El cuerpo del request está vacío.");
            }

            JsonReader reader = Json.createReader(new StringReader(body));
            JsonObject json = reader.readObject();

            String descripcion = json.getString("descripcion");
            double precio = json.getJsonNumber("precio").doubleValue();

            Products nuevo = new Products(nextId++, descripcion, precio);
            productos.add(nuevo);

            JsonObject success = Json.createObjectBuilder()
                    .add("mensaje", "Producto guardado correctamente")
                    .add("idGenerado", nuevo.getIdProduct())
                    .build();

            return Response
                    .status(Response.Status.CREATED)
                    .entity(success.toString())
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Json.createObjectBuilder()
                            .add("error", e.getMessage())
                            .build()
                            .toString())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
