package ec.edu.gr03.model;

import ec.edu.gr03.controller.Cuenta;
import ec.edu.gr03.controller.Movimiento;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EurekaBankClient {
    
    private static final String BASE_URI = "http://10.181.89.248:60245/api/movimiento";
    
    public static List<Movimiento> traerMovimientos(String cuenta) {
        Client client = ClientBuilder.newClient();
        try {
            WebTarget target = client.target(BASE_URI)
                    .path("listar")
                    .queryParam("cuenta", cuenta);
            
            Movimiento[] array = target
                    .request(MediaType.APPLICATION_JSON)
                    .get(Movimiento[].class);
            
            List<Movimiento> lista = new ArrayList<>(Arrays.asList(array));
            
            // Ordenar por fecha de MAYOR a MENOR (más reciente primero)
            Collections.sort(lista, new Comparator<Movimiento>() {
                @Override
                public int compare(Movimiento m1, Movimiento m2) {
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
    
    public static String regDeposito(String cuenta, Double importe) {
        Client client = ClientBuilder.newClient();
        try {
            WebTarget target = client.target(BASE_URI).path("deposito");
            String json = "{\"cuenta\":\"" + cuenta + "\",\"importe\":" + String.valueOf(importe) + "}";
            
            Response response = target
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(json, MediaType.APPLICATION_JSON), Response.class);
            
            return response.getStatus() == 200 ? "1" : "0";
        } catch (Exception e) {
            System.err.println("Error en depósito: " + e.getMessage());
            return "0";
        } finally {
            client.close();
        }
    }
    
    public static String regRetiro(String cuenta, Double importe) {
        Client client = ClientBuilder.newClient();
        try {
            WebTarget target = client.target(BASE_URI).path("retiro");
            String json = "{\"cuenta\":\"" + cuenta + "\",\"importe\":" + String.valueOf(importe) + "}";
            
            Response response = target
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(json, MediaType.APPLICATION_JSON), Response.class);
            
            return response.getStatus() == 200 ? "1" : "0";
        } catch (Exception e) {
            System.err.println("Error en retiro: " + e.getMessage());
            return "0";
        } finally {
            client.close();
        }
    }
    
    public static String regTransferencia(String cuentaOrigen, String cuentaDestino, Double importe) {
        Client client = ClientBuilder.newClient();
        try {
            WebTarget target = client.target(BASE_URI).path("transferencia");
            String json = "{\"cuentaOrigen\":\"" + cuentaOrigen + "\",\"cuentaDestino\":\"" + cuentaDestino + "\",\"importe\":" + String.valueOf(importe) + "}";
            
            Response response = target
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(json, MediaType.APPLICATION_JSON), Response.class);
            
            return response.getStatus() == 200 ? "1" : "0";
        } catch (Exception e) {
            System.err.println("Error en transferencia: " + e.getMessage());
            return "0";
        } finally {
            client.close();
        }
    }
    
    public static Boolean login(String username, String password) {
        Client client = ClientBuilder.newClient();
        try {
            WebTarget target = client.target(BASE_URI).path("login");
            String json = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
            
            Response response = target
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(json, MediaType.APPLICATION_JSON), Response.class);
            
            if (response.getStatus() == 200) {
                String result = response.readEntity(String.class);
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
    
    public static List<Cuenta> traerBalances() {
        Client client = ClientBuilder.newClient();
        try {
            WebTarget target = client.target(BASE_URI).path("balances");
            
            Cuenta[] array = target
                    .request(MediaType.APPLICATION_JSON)
                    .get(Cuenta[].class);
            
            return new ArrayList<>(Arrays.asList(array));
        } catch (Exception e) {
            System.err.println("Error al traer balances: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            client.close();
        }
    }
    
    public static double ObtenerSaldo(List<Movimiento> movimientos) {
        double saldo = 0;
        for (Movimiento movimiento : movimientos) {
            if(movimiento.getAccion().equals("INGRESO")) {
                saldo += movimiento.getImporte();
            } else {
                saldo -= movimiento.getImporte();
            }
        }
        return saldo;
    }
}
