package ec.edu.espe.soap_conuni_clmov_gro3.arguello.servicios;

import android.util.Log;
import ec.edu.espe.soap_conuni_clmov_gro3.arguello.Config.SoapClientConfig;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ConUniServicio {

    private static final String TAG = "ConUniServicio";
    private final SoapClientConfig soapClient;

    public ConUniServicio() {
        this.soapClient = new SoapClientConfig("WSConUni");
    }

    public double conversion(double value, String inUnit, String outUnit) {
        Log.d(TAG, "Convirtiendo: " + value + " " + inUnit + " -> " + outUnit);
        String soapRequest = buildSoapRequest(value, inUnit, outUnit);
        Log.d(TAG, "SOAP Request generado");
        String response = soapClient.call(soapRequest);
        Log.d(TAG, "Respuesta recibida, parseando...");
        double result = parseSoapResponse(response);
        Log.d(TAG, "Resultado de conversión: " + result);
        return result;
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

    private double parseSoapResponse(String xml) {
        try {
            Log.d(TAG, "Parseando respuesta SOAP...");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            doc.getDocumentElement().normalize();

            if (doc.getElementsByTagNameNS("http://ws.arguello.edu.ec/", "return").getLength() > 0) {
                String text = doc.getElementsByTagNameNS("http://ws.arguello.edu.ec/", "return").item(0).getTextContent();
                Log.d(TAG, "Valor encontrado en return (namespace): " + text);
                return Double.parseDouble(text.trim());
            }

            if (doc.getElementsByTagName("return").getLength() > 0) {
                String text = doc.getElementsByTagName("return").item(0).getTextContent();
                Log.d(TAG, "Valor encontrado en return: " + text);
                return Double.parseDouble(text.trim());
            }

            String[] candidateTags = new String[]{"convertUnitResponse", "result"};
            for (String tag : candidateTags) {
                if (doc.getElementsByTagName(tag).getLength() > 0) {
                    String text = doc.getElementsByTagName(tag).item(0).getTextContent();
                    Log.d(TAG, "Valor encontrado en " + tag + ": " + text);
                    return Double.parseDouble(text.trim());
                }
            }

            if (doc.getElementsByTagNameNS("http://schemas.xmlsoap.org/soap/envelope/", "Body").getLength() > 0) {
                org.w3c.dom.Node body = doc.getElementsByTagNameNS("http://schemas.xmlsoap.org/soap/envelope/", "Body").item(0);
                String text = body.getTextContent();
                java.util.Scanner s = new java.util.Scanner(text).useDelimiter("[^0-9.+-eE]");
                if (s.hasNextDouble()) {
                    double result = s.nextDouble();
                    Log.d(TAG, "Valor encontrado en Body (scanner): " + result);
                    return result;
                }
            }

            Log.e(TAG, "No se pudo parsear la respuesta SOAP: " + xml);
            throw new RuntimeException("No se pudo parsear la respuesta SOAP: " + xml);
        } catch (Exception ex) {
            Log.e(TAG, "Error al procesar la respuesta SOAP", ex);
            throw new RuntimeException("Error al procesar la respuesta SOAP", ex);
        }
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
