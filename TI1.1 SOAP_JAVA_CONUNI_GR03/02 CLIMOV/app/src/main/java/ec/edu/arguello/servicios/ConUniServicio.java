package ec.edu.arguello.servicios;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ConUniServicio {

    private static final String ENDPOINT = "http://10.0.2.2:8080/WS_ConUni_SOAPJAVA_GR03/WSConUni";
    private static final MediaType XML = MediaType.get("text/xml; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient();

    public double conversion(double value, String inUnit, String outUnit) {
        try {
            String soapRequest = buildSoapRequest(value, inUnit, outUnit);
            RequestBody body = RequestBody.create(soapRequest, XML);

            Request request = new Request.Builder()
                    .url(ENDPOINT)
                    .post(body)
                    .addHeader("Content-Type", "text/xml; charset=utf-8")
                    // Si el servicio requiere SOAPAction, descomente y ajuste:
                    //.addHeader("SOAPAction", "convertUnit")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RuntimeException("HTTP error: " + response.code() + " - " + response.message());
                }
                String respBody = response.body() != null ? response.body().string() : "";
                return parseSoapResponse(respBody);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error invoking SOAP service", ex);
        }
    }

    private String buildSoapRequest(double value, String inUnit, String outUnit) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.arguello.edu.ec/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<ws:convertUnit>" +
                "<value>" + value + "</value>" +
                "<inUnit>" + escapeXml(inUnit) + "</inUnit>" +
                "<outUnit>" + escapeXml(outUnit) + "</outUnit>" +
                "</ws:convertUnit>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    private double parseSoapResponse(String xml) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        doc.getDocumentElement().normalize();

        // Buscar el elemento <return> dentro de <convertUnitResponse>
        // Primero intentar con namespace
        if (doc.getElementsByTagNameNS("http://ws.arguello.edu.ec/", "return").getLength() > 0) {
            String text = doc.getElementsByTagNameNS("http://ws.arguello.edu.ec/", "return").item(0).getTextContent();
            return Double.parseDouble(text.trim());
        }
        
        // Luego intentar sin namespace (para compatibilidad)
        if (doc.getElementsByTagName("return").getLength() > 0) {
            String text = doc.getElementsByTagName("return").item(0).getTextContent();
            return Double.parseDouble(text.trim());
        }

        // Búsqueda alternativa en otros tags comunes
        String[] candidateTags = new String[]{"convertUnitResponse", "result"};
        for (String tag : candidateTags) {
            if (doc.getElementsByTagName(tag).getLength() > 0) {
                String text = doc.getElementsByTagName(tag).item(0).getTextContent();
                return Double.parseDouble(text.trim());
            }
        }

        // Último recurso: buscar cualquier número en el Body
        if (doc.getElementsByTagNameNS("http://schemas.xmlsoap.org/soap/envelope/", "Body").getLength() > 0) {
            org.w3c.dom.Node body = doc.getElementsByTagNameNS("http://schemas.xmlsoap.org/soap/envelope/", "Body").item(0);
            String text = body.getTextContent();
            // intentar extraer número
            java.util.Scanner s = new java.util.Scanner(text).useDelimiter("[^0-9.+-eE]");
            if (s.hasNextDouble()) {
                return s.nextDouble();
            }
        }

        throw new RuntimeException("No se pudo parsear la respuesta SOAP: " + xml);
    }

    private String escapeXml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
