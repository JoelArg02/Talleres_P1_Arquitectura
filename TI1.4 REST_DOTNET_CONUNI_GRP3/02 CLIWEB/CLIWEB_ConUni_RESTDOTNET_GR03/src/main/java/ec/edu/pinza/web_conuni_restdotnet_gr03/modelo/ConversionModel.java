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

    public List<UnitOption> obtenerUnidades() {
        return UnitOption.longitud();
    }

    public ConversionResult convertir(double valor, String slugOrigen, String slugDestino) throws ConversionException {
        UnitOption unidadOrigen = UnitOption.findBySlug(slugOrigen)
                .orElseThrow(() -> new ConversionException("Unidad de origen no valida."));
        UnitOption unidadDestino = UnitOption.findBySlug(slugDestino)
                .orElseThrow(() -> new ConversionException("Unidad de destino no valida."));

        String payload = String.format(
                Locale.US,
                "{\n"
                + "  \"value\": %.6f,\n"
                + "  \"fromUnit\": %d,\n"
                + "  \"toUnit\": %d\n"
                + "}",
                valor,
                unidadOrigen.getCodigoApi(),
                unidadDestino.getCodigoApi()
        );

        try {
            HttpResponse<String> response = restClient.postLengthConversion(payload);
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
            String category = json.getString("category", "Longitud");
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
