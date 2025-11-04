package ec.edu.pinza.web_conuni_restjava_gr03.modelo;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConversionModel {

    private static final String DEFAULT_TYPE = "longitud";
    private static final List<String> FALLBACK_UNITS = List.of(
            "metros",
            "kilometros",
            "centimetros"
    );

    private final RestClient restClient = new RestClient();

    public Double convertir(double valor, String tipo, String inUnit, String outUnit) {
        String tipoNormalizado = normalizarTipo(tipo);

        JsonObject payload = Json.createObjectBuilder()
                .add("type", tipoNormalizado)
                .add("value", valor)
                .add("inUnit", inUnit)
                .add("outUnit", outUnit)
                .build();

        try {
            HttpResponse<String> response = restClient.post("/ConUni/convertUnit", payload.toString());

            if (response.statusCode() == 200) {
                String body = response.body();
                if (body != null && !body.isBlank()) {
                    return Double.parseDouble(body.trim());
                }
            } else {
                System.err.println("Conversion REST fallida, codigo: " + response.statusCode());
            }
        } catch (IOException e) {
            System.err.println("Error al conectar con ConUni REST: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Solicitud interrumpida hacia ConUni REST");
        } catch (NumberFormatException e) {
            System.err.println("Respuesta invalida del servicio ConUni REST: " + e.getMessage());
        }

        return null;
    }

    public List<String> obtenerUnidades(String tipo) {
        String tipoNormalizado = normalizarTipo(tipo);

        try {
            HttpResponse<String> response = restClient.get("/ConUni/supportedUnits/" + RestClient.encodePathSegment(tipoNormalizado));
            if (response.statusCode() == 200) {
                String body = response.body();
                if (body != null && !body.isBlank()) {
                    try (JsonReader reader = Json.createReader(new StringReader(body))) {
                        JsonArray array = reader.readArray();
                        List<String> units = new ArrayList<>(array.size());
                        for (int i = 0; i < array.size(); i++) {
                            units.add(array.getString(i));
                        }
                        return units;
                    }
                }
            } else {
                System.err.println("No se pudieron obtener unidades REST, codigo: " + response.statusCode());
            }
        } catch (IOException e) {
            System.err.println("Error al obtener unidades REST: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Solicitud interrumpida al obtener unidades REST");
        }

        return Collections.emptyList();
    }

    public List<String> obtenerUnidadesConFallback(String tipo) {
        List<String> unidades = obtenerUnidades(tipo);
        return unidades.isEmpty() ? FALLBACK_UNITS : unidades;
    }

    public String getTipoPorDefecto() {
        return DEFAULT_TYPE;
    }

    private String normalizarTipo(String tipo) {
        return (tipo == null || tipo.isBlank()) ? DEFAULT_TYPE : tipo.toLowerCase();
    }
}
