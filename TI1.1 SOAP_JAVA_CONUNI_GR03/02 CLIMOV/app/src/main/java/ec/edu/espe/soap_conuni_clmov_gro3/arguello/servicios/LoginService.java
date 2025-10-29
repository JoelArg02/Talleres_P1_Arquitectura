package ec.edu.espe.soap_conuni_clmov_gro3.arguello.servicios;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import ec.edu.espe.soap_conuni_clmov_gro3.arguello.Config.SoapClientConfig;

public class LoginService {

    private final SoapClientConfig soapClient;

    public LoginService() {
        this.soapClient = new SoapClientConfig("/WSLogin");
    }

    public boolean login(String username, String password) {
        String soapRequest = buildSoapRequest(username, password);
        String response = soapClient.call(soapRequest);
        return parseSoapResponse(response);
    }

    private String buildSoapRequest(String username, String password) {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.arguello.edu.ec/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<ws:login>" +
                "<username>" + escapeXml(username) + "</username>" +
                "<password>" + escapeXml(password) + "</password>" +
                "</ws:login>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    private boolean parseSoapResponse(String xml) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            doc.getDocumentElement().normalize();

            if (doc.getElementsByTagNameNS("http://ws.arguello.edu.ec/", "return").getLength() > 0) {
                String text = doc.getElementsByTagNameNS("http://ws.arguello.edu.ec/", "return").item(0).getTextContent();
                return Boolean.parseBoolean(text.trim());
            }

            if (doc.getElementsByTagName("return").getLength() > 0) {
                String text = doc.getElementsByTagName("return").item(0).getTextContent();
                return Boolean.parseBoolean(text.trim());
            }

            String[] candidateTags = new String[]{"loginResponse", "result"};
            for (String tag : candidateTags) {
                if (doc.getElementsByTagName(tag).getLength() > 0) {
                    String text = doc.getElementsByTagName(tag).item(0).getTextContent();
                    return Boolean.parseBoolean(text.trim());
                }
            }

            throw new RuntimeException("No se pudo parsear la respuesta SOAP: " + xml);
        } catch (Exception ex) {
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
