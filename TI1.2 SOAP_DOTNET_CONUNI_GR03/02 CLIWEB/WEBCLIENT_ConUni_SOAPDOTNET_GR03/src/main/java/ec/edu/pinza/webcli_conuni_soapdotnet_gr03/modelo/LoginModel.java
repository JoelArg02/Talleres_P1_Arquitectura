package ec.edu.pinza.webcli_conuni_soapdotnet_gr03.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class LoginModel {

    private static final String ENDPOINT = "http://localhost:5001/Login.svc";
    private static final String SOAP_ACTION = "http://tempuri.org/ILoginService/ValidarCredenciales";

    public LoginResponse autenticar(String usuario, String contrasena) {
        try {
            String payload = buildRequestPayload(usuario, contrasena);
            String responseXml = invokeSoapEndpoint(payload);
            if (responseXml == null) {
                return new LoginResponse(false, "No se pudo contactar el servicio de autenticacion.", null);
            }
            return parseResponse(responseXml);
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            System.err.println("Error al invocar servicio SOAP de login: " + ex.getMessage());
            return new LoginResponse(false, "No se pudo contactar el servicio de autenticacion.", null);
        }
    }

    private String buildRequestPayload(String usuario, String contrasena) {
        return """
                <?xml version="1.0" encoding="utf-8"?>
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                  xmlns:tem="http://tempuri.org/">
                  <soapenv:Header/>
                  <soapenv:Body>
                    <tem:ValidarCredenciales>
                      <tem:username>%s</tem:username>
                      <tem:password>%s</tem:password>
                    </tem:ValidarCredenciales>
                  </soapenv:Body>
                </soapenv:Envelope>
                """.formatted(
                SoapXmlUtils.escapeXml(Optional.ofNullable(usuario).orElse("")),
                SoapXmlUtils.escapeXml(Optional.ofNullable(contrasena).orElse(""))
        );
    }

    private String invokeSoapEndpoint(String payload) throws IOException {
        URL url = new URL(ENDPOINT);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        connection.setRequestProperty("SOAPAction", SOAP_ACTION);

        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            writer.write(payload);
        }

        int status = connection.getResponseCode();
        InputStream input = status >= 400 ? connection.getErrorStream() : connection.getInputStream();
        if (input == null) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } finally {
            connection.disconnect();
        }
    }

    private LoginResponse parseResponse(String xml)
            throws ParserConfigurationException, IOException, SAXException {

        Document document = SoapXmlUtils.parseXml(xml);
        Element envelope = document.getDocumentElement();
        Element body = SoapXmlUtils.findFirstChild(envelope, "Body");
        if (body == null) {
            return new LoginResponse(false, "Respuesta sin cuerpo SOAP.", null);
        }

        Element fault = SoapXmlUtils.findFirstChild(body, "Fault");
        if (fault != null) {
            String faultString = SoapXmlUtils.getChildText(fault, "faultstring");
            if (faultString == null) {
                faultString = SoapXmlUtils.getChildText(fault, "faultcode");
            }
            return new LoginResponse(false, "Error SOAP: " + (faultString != null ? faultString : "Fault desconocido"), null);
        }

        Element responseElement = SoapXmlUtils.findFirstChild(body, "ValidarCredencialesResponse");
        if (responseElement == null) {
            return new LoginResponse(false, "Estructura de respuesta inesperada.", null);
        }
        Element resultElement = SoapXmlUtils.findFirstChild(responseElement, "ValidarCredencialesResult");
        if (resultElement == null) {
            return new LoginResponse(false, "Estructura de respuesta inesperada.", null);
        }

        Boolean success = SoapXmlUtils.getChildBoolean(resultElement, "Success");
        String message = SoapXmlUtils.getChildText(resultElement, "Message");
        String token = SoapXmlUtils.getChildText(resultElement, "Token");

        boolean ok = Boolean.TRUE.equals(success);
        if (!ok && (message == null || message.isBlank())) {
            message = "Credenciales invalidas.";
        }

        return new LoginResponse(ok, message, token);
    }
}
