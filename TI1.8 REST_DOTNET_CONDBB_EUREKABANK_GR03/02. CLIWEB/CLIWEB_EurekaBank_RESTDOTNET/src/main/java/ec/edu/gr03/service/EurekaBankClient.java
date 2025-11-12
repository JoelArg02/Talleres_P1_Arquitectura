package ec.edu.gr03.service;

import ec.edu.gr03.model.Cuenta;
import ec.edu.gr03.model.Movimiento;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Cliente para consumir el servicio REST DOTNET de EurekaBank
 * Esta clase encapsula todas las operaciones disponibles en la REST API
 * 
 * NOTA IMPORTANTE:
 * Antes de usar esta clase, debe:
 * 1. Asegurarse que el servidor REST DOTNET esté corriendo en http://localhost:60245/
 * 2. Las clases modelo están en el paquete ec.edu.gr03.model
 * 
 * API Base: http://localhost:60245/api/movimiento
 */
public class EurekaBankClient {
    
    private static final String BASE_URI = "http://localhost:60245/api/movimiento";
    
    /**
     * Realiza login de un usuario
     * @param username nombre de usuario
     * @param password contraseña
     * @return true si el login es exitoso, false en caso contrario
     */
    public static boolean login(String username, String password) {
        Client client = ClientBuilder.newClient();
        try {
            WebTarget target = client.target(BASE_URI).path("login");
            String json = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
            
            Response response = target
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(json, MediaType.APPLICATION_JSON), Response.class);
            
            if (response.getStatus() == 200) {
                String result = response.readEntity(String.class);
                // Remover espacios en blanco y saltos de línea para comparar
                return result.replaceAll("\\s+", "").contains("\"autenticado\":true");
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error en login: " + e.getMessage());
            return false;
        } finally {
            client.close();
        }
    }
    
    /**
     * Registra un depósito en una cuenta
     * @param cuenta número de cuenta
     * @param importe monto a depositar
     * @return 1 si fue exitoso, -1 en caso de error
     */
    public static int regDeposito(String cuenta, double importe) {
        Client client = ClientBuilder.newClient();
        try {
            WebTarget target = client.target(BASE_URI).path("deposito");
            String json = "{\"cuenta\":\"" + cuenta + "\",\"importe\":" + String.valueOf(importe) + "}";
            
            Response response = target
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(json, MediaType.APPLICATION_JSON), Response.class);
            
            return response.getStatus() == 200 ? 1 : -1;
        } catch (Exception e) {
            System.err.println("Error en depósito: " + e.getMessage());
            return -1;
        } finally {
            client.close();
        }
    }
    
    /**
     * Registra un retiro de una cuenta
     * @param cuenta número de cuenta
     * @param importe monto a retirar
     * @return 1 si fue exitoso, -1 en caso de error
     */
    public static int regRetiro(String cuenta, double importe) {
        Client client = ClientBuilder.newClient();
        try {
            WebTarget target = client.target(BASE_URI).path("retiro");
            String json = "{\"cuenta\":\"" + cuenta + "\",\"importe\":" + String.valueOf(importe) + "}";
            
            Response response = target
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(json, MediaType.APPLICATION_JSON), Response.class);
            
            return response.getStatus() == 200 ? 1 : -1;
        } catch (Exception e) {
            System.err.println("Error en retiro: " + e.getMessage());
            return -1;
        } finally {
            client.close();
        }
    }
    
    /**
     * Registra una transferencia entre dos cuentas
     * @param cuentaOrigen cuenta desde donde se transfiere
     * @param cuentaDestino cuenta hacia donde se transfiere
     * @param importe monto a transferir
     * @return 1 si fue exitoso, -1 en caso de error
     */
    public static int regTransferencia(String cuentaOrigen, String cuentaDestino, double importe) {
        Client client = ClientBuilder.newClient();
        try {
            WebTarget target = client.target(BASE_URI).path("transferencia");
            String json = "{\"cuentaOrigen\":\"" + cuentaOrigen + "\",\"cuentaDestino\":\"" + cuentaDestino + "\",\"importe\":" + String.valueOf(importe) + "}";
            
            Response response = target
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(json, MediaType.APPLICATION_JSON), Response.class);
            
            return response.getStatus() == 200 ? 1 : -1;
        } catch (Exception e) {
            System.err.println("Error en transferencia: " + e.getMessage());
            return -1;
        } finally {
            client.close();
        }
    }
    
    /**
     * Obtiene todos los movimientos de una cuenta
     * @param cuenta número de cuenta
     * @return lista de movimientos
     */
    public static List<Movimiento> traerMovimientos(String cuenta) {
        Client client = ClientBuilder.newClient().register(JacksonFeature.class);
        try {
            WebTarget target = client.target(BASE_URI)
                    .path("listar")
                    .queryParam("cuenta", cuenta);
            
            System.out.println("DEBUG: Llamando a URL: " + target.getUri());
            
            Response response = target
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            
            String jsonString = response.readEntity(String.class);
            System.out.println("DEBUG: JSON recibido de movimientos: " + jsonString);
            
            // Deserializar usando ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            Movimiento[] array = mapper.readValue(jsonString, Movimiento[].class);
            
            System.out.println("DEBUG: Movimientos deserializados: " + array.length);
            if (array.length > 0) {
                System.out.println("DEBUG: Primer movimiento - NroMov: " + array[0].getNroMov() + ", Tipo: " + array[0].getTipo());
            }
            
            List<Movimiento> lista = new ArrayList<>(Arrays.asList(array));
            
            // Ordenar por fecha de MAYOR a MENOR (más reciente primero)
            Collections.sort(lista, new Comparator<Movimiento>() {
                @Override
                public int compare(Movimiento m1, Movimiento m2) {
                    // Comparar fechas en orden descendente (más reciente primero)
                    if (m1.getFecha() == null && m2.getFecha() == null) return 0;
                    if (m1.getFecha() == null) return 1;
                    if (m2.getFecha() == null) return -1;
                    return m2.getFecha().compareTo(m1.getFecha());
                }
            });
            
            return lista;
        } catch (Exception e) {
            System.err.println("Error al traer movimientos: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            client.close();
        }
    }
    
    /**
     * Obtiene los balances de todas las cuentas activas
     * @return lista de cuentas con sus balances
     */
    public static List<Cuenta> traerBalances() {
        Client client = ClientBuilder.newClient().register(JacksonFeature.class);
        try {
            WebTarget target = client.target(BASE_URI).path("balances");
            
            System.out.println("DEBUG: Llamando a URL: " + target.getUri());
            
            Response response = target
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            
            String jsonString = response.readEntity(String.class);
            System.out.println("DEBUG: JSON recibido de balances: " + jsonString);
            
            // Deserializar usando ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            Cuenta[] array = mapper.readValue(jsonString, Cuenta[].class);
            
            System.out.println("DEBUG: Cuentas deserializadas: " + array.length);
            if (array.length > 0) {
                System.out.println("DEBUG: Primera cuenta - Numero: " + array[0].getNumeroCuenta() + ", Cliente: " + array[0].getNombreCliente());
            }
            
            return new ArrayList<>(Arrays.asList(array));
        } catch (Exception e) {
            System.err.println("Error al traer balances: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            client.close();
        }
    }
}
