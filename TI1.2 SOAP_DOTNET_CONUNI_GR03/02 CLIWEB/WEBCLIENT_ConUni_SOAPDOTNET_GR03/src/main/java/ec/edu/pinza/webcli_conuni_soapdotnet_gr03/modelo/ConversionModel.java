package ec.edu.pinza.webcli_conuni_soapdotnet_gr03.modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ConversionModel {

    private static final String ENDPOINT = "http://localhost:5001/Conversion.svc";
    private static final String SOAP_ACTION = "http://tempuri.org/IService/Convert";

    public ConversionResponse convertir(double masaKg) {
        try {
            String payload = buildRequestPayload(masaKg);
            String responseXml = invokeSoapEndpoint(payload);
            if (responseXml == null) {
                return null;
            }
            return parseResponse(responseXml);
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            System.err.println("Error al invocar servicio SOAP de conversion: " + ex.getMessage());
            return null;
        }
    }

    private String buildRequestPayload(double masaKg) {
        return """
                <?xml version="1.0" encoding="utf-8"?>
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                  xmlns:tem="http://tempuri.org/"
                                  xmlns:mod="http://schemas.datacontract.org/2004/07/WCFService.Models">
                  <soapenv:Header/>
                  <soapenv:Body>
                    <tem:Convert>
                      <tem:request>
                        <mod:MassKg>%s</mod:MassKg>
                      </tem:request>
                    </tem:Convert>
                  </soapenv:Body>
                </soapenv:Envelope>
                """.formatted(Double.toString(masaKg));
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

    private ConversionResponse parseResponse(String xml)
            throws ParserConfigurationException, IOException, SAXException {

        Document document = SoapXmlUtils.parseXml(xml);
        Element envelope = document.getDocumentElement();
        Element body = SoapXmlUtils.findFirstChild(envelope, "Body");
        if (body == null) {
            return null;
        }

        Element fault = SoapXmlUtils.findFirstChild(body, "Fault");
        if (fault != null) {
            String faultString = SoapXmlUtils.getChildText(fault, "faultstring");
            if (faultString == null) {
                faultString = SoapXmlUtils.getChildText(fault, "faultcode");
            }
            System.err.println("Fault del servicio de conversion: " + (faultString != null ? faultString : "Fault desconocido"));
            return null;
        }

        Element responseElement = SoapXmlUtils.findFirstChild(body, "ConvertResponse");
        if (responseElement == null) {
            System.err.println("Respuesta de conversion sin ConvertResponse.");
            return null;
        }
        Element resultElement = SoapXmlUtils.findFirstChild(responseElement, "ConvertResult");
        if (resultElement == null) {
            System.err.println("Respuesta de conversion sin ConvertResult.");
            return null;
        }

        Double massKg = SoapXmlUtils.getChildDouble(resultElement, "MassKg");
        Double massLb = SoapXmlUtils.getChildDouble(resultElement, "MassLb");
        Double massG = SoapXmlUtils.getChildDouble(resultElement, "MassG");

        Double latitudeDecimal = SoapXmlUtils.getChildDouble(resultElement, "LatitudeDecimal");
        Double latitudeRadians = SoapXmlUtils.getChildDouble(resultElement, "LatitudeRadians");
        CoordinateDMS latitudeDms = parseCoordinate(resultElement, "LatitudeDMS");

        Double longitudeDecimal = SoapXmlUtils.getChildDouble(resultElement, "LongitudeDecimal");
        Double longitudeRadians = SoapXmlUtils.getChildDouble(resultElement, "LongitudeRadians");
        CoordinateDMS longitudeDms = parseCoordinate(resultElement, "LongitudeDMS");

        return new ConversionResponse(
                massKg,
                massLb,
                massG,
                latitudeDecimal,
                latitudeRadians,
                latitudeDms,
                longitudeDecimal,
                longitudeRadians,
                longitudeDms
        );
    }

    private CoordinateDMS parseCoordinate(Element parent, String elementName) {
        Element coordElement = SoapXmlUtils.findFirstChild(parent, elementName);
        if (coordElement == null || SoapXmlUtils.isNil(coordElement)) {
            return null;
        }
        Integer degrees = SoapXmlUtils.getChildInteger(coordElement, "Degrees");
        Integer minutes = SoapXmlUtils.getChildInteger(coordElement, "Minutes");
        Double seconds = SoapXmlUtils.getChildDouble(coordElement, "Seconds");
        String hemisphere = SoapXmlUtils.getChildText(coordElement, "Hemisphere");
        CoordinateDMS coordinate = new CoordinateDMS(degrees, minutes, seconds, hemisphere);
        return coordinate.isDefined() ? coordinate : null;
    }
}
