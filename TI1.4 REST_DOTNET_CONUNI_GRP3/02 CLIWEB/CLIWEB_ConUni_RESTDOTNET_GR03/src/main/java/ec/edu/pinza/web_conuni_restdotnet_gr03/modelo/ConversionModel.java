package ec.edu.pinza.web_conuni_restdotnet_gr03.modelo;

import ec.edu.pinza.web_conuni_restdotnet_gr03.modelo.dto.ConversionResult;
import ec.edu.pinza.web_conuni_restdotnet_gr03.modelo.dto.UnitOption;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

public class ConversionModel {

    private final RestClient restClient;

    public ConversionModel() {
        this(new RestClient());
    }

    ConversionModel(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<UnitOption> obtenerUnidades(String tipo) {
        return UnitOption.porTipo(tipo);
    }

    public ConversionResult convertir(double valor, String slugOrigen, String slugDestino, String tipo) throws ConversionException {
        UnitOption unidadOrigen = UnitOption.findBySlug(slugOrigen, tipo)
                .orElseThrow(() -> new ConversionException("Unidad de origen no valida."));
        UnitOption unidadDestino = UnitOption.findBySlug(slugDestino, tipo)
                .orElseThrow(() -> new ConversionException("Unidad de destino no valida."));

        String payload = String.format(
                Locale.US,
                "{\n"
                + "  \"value\": %.6f,\n"
                + "  \"fromUnit\": \"%s\",\n"
                + "  \"toUnit\": \"%s\"\n"
                + "}",
                valor,
                unidadOrigen.getApiValue(),
                unidadDestino.getApiValue()
        );

        try {
            HttpResponse<String> response = callApiByType(tipo, payload);
            int status = response.statusCode();
            String body = response.body();

            if (status == 200) {
                return parseConversion(body, unidadOrigen, unidadDestino);
            }

            throw new ConversionException(buildErrorMessage(status, body));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ConversionException("La solicitud de conversion fue interrumpida.", e);
        } catch (IOException e) {
            throw new ConversionException("Error al comunicarse con el servicio de conversion.", e);
        }
    }

    private HttpResponse<String> callApiByType(String tipo, String payload) throws IOException, InterruptedException {
        if (tipo == null || tipo.isBlank()) {
            return restClient.postWeightConversion(payload);
        }
        String tipoLower = tipo.toLowerCase(Locale.ROOT);
        if ("masa".equals(tipoLower) || "weight".equals(tipoLower)) {
            return restClient.postWeightConversion(payload);
        } else if ("temperatura".equals(tipoLower) || "temperature".equals(tipoLower)) {
            return restClient.postTemperatureConversion(payload);
        } else if ("longitud".equals(tipoLower) || "length".equals(tipoLower)) {
            return restClient.postLengthConversion(payload);
        } else {
            return restClient.postWeightConversion(payload);
        }
    }

    private ConversionResult parseConversion(String body, UnitOption unidadOrigen, UnitOption unidadDestino) throws ConversionException {
        if (body == null || body.isBlank()) {
            throw new ConversionException("El servicio devolvio una respuesta vacia.");
        }
        try (JsonReader reader = Json.createReader(new StringReader(body))) {
            JsonObject json = reader.readObject();
            double originalValue = json.containsKey("originalValue")
                    ? json.getJsonNumber("originalValue").doubleValue()
                    : Double.NaN;
            double convertedValue = json.containsKey("convertedValue")
                    ? json.getJsonNumber("convertedValue").doubleValue()
                    : Double.NaN;
            String fromUnit = json.getString("fromUnit", unidadOrigen.getEtiqueta());
            String toUnit = json.getString("toUnit", unidadDestino.getEtiqueta());
            String category = json.getString("category", "");
            String timestampRaw = json.getString("timestamp", null);
            OffsetDateTime timestamp = null;
            if (timestampRaw != null && !timestampRaw.isBlank()) {
                try {
                    timestamp = OffsetDateTime.parse(timestampRaw);
                } catch (DateTimeParseException ex) {
                    timestamp = null;
                }
            }

            if (Double.isNaN(originalValue) || Double.isNaN(convertedValue)) {
                throw new ConversionException("El servicio devolvio datos incompletos.");
            }
            return new ConversionResult(originalValue, fromUnit, convertedValue, toUnit, category, timestamp);
        } catch (IllegalStateException e) {
            throw new ConversionException("La respuesta del servicio no es un objeto JSON valido.", e);
        }
    }

    private String buildErrorMessage(int status, String body) {
        StringBuilder builder = new StringBuilder("No se pudo realizar la conversion. Codigo HTTP: ").append(status);
        if (body != null && !body.isBlank()) {
            try (JsonReader reader = Json.createReader(new StringReader(body))) {
                JsonObject json = reader.readObject();
                String message = json.getString("message", null);
                String details = json.getString("details", null);
                if (message != null && !message.isBlank()) {
                    builder.append(" - ").append(message);
                }
                if (details != null && !details.isBlank()) {
                    builder.append(" (").append(details).append(')');
                }
            } catch (Exception ignored) {
                builder.append(". Respuesta: ").append(body.trim());
            }
        }
        return builder.toString();
    }
}
