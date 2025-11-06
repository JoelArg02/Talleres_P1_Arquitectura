package ec.edu.pinza.clicon_conuni_soapdotnet_gr03.modelo;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPConnection;
import jakarta.xml.soap.SOAPConnectionFactory;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import java.util.Iterator;
import javax.xml.namespace.QName;

public class ConversionModel {

    private static final String NAMESPACE_TEMPURI = "http://tempuri.org/";
    private static final String PREFIX_TEMPURI = "tem";
    private static final String CONVERSION_ENDPOINT = "http://localhost:5001/Conversion.svc";

    public double conversionUnidades(double valor, String unidadOrigen, String unidadDestino) {
        try {
            SOAPMessage request = buildRequest(valor, unidadOrigen, unidadDestino);
            SOAPConnection connection = SOAPConnectionFactory.newInstance().createConnection();
            try {
                SOAPMessage response = connection.call(request, CONVERSION_ENDPOINT);
                return parseResponse(response);
            } finally {
                connection.close();
            }
        } catch (Exception e) {
            System.err.println("Error al convertir unidades: " + e.getMessage());
            throw new RuntimeException("Error al convertir unidades: " + e.getMessage(), e);
        }
    }

    private SOAPMessage buildRequest(double value, String inUnit, String outUnit) throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        message.getMimeHeaders().addHeader("SOAPAction", "\"http://tempuri.org/IService/ConvertUnit\"");
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        envelope.addNamespaceDeclaration(PREFIX_TEMPURI, NAMESPACE_TEMPURI);

        SOAPBody body = envelope.getBody();
        SOAPElement convertElement = body.addChildElement("ConvertUnit", PREFIX_TEMPURI);
        convertElement.addChildElement("value", PREFIX_TEMPURI).addTextNode(Double.toString(value));
        convertElement.addChildElement("inUnit", PREFIX_TEMPURI).addTextNode(inUnit);
        convertElement.addChildElement("outUnit", PREFIX_TEMPURI).addTextNode(outUnit);

        message.saveChanges();
        return message;
    }

    private double parseResponse(SOAPMessage response) throws SOAPException {
        SOAPBody body = response.getSOAPBody();
        SOAPElement responseElement = firstChild(body, new QName(NAMESPACE_TEMPURI, "ConvertUnitResponse"));
        if (responseElement == null) {
            throw new SOAPException("Respuesta inesperada del servicio de conversion.");
        }

        SOAPElement resultElement = firstChild(responseElement, new QName(NAMESPACE_TEMPURI, "ConvertUnitResult"));
        if (resultElement == null) {
            throw new SOAPException("El servicio no devolvio resultados de conversion.");
        }

        String resultText = resultElement.getTextContent();
        if (resultText == null || resultText.isBlank()) {
            throw new SOAPException("El resultado de conversion esta vacio.");
        }

        try {
            return Double.parseDouble(resultText);
        } catch (NumberFormatException e) {
            throw new SOAPException("El resultado no es un numero valido: " + resultText, e);
        }
    }

    private SOAPElement firstChild(SOAPElement parent, QName name) {
        Iterator<?> iterator = parent.getChildElements(name);
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next instanceof SOAPElement element) {
                return element;
            }
        }
        return null;
    }
}
