package ec.edu.pinza.clicon_conuni_soapdotnet_gr03.modelo;

import ec.edu.pinza.clicon_conuni_soapdotnet_gr03.modelo.dto.ConversionResult;
import ec.edu.pinza.clicon_conuni_soapdotnet_gr03.modelo.dto.CoordinateDms;
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
    private static final String NAMESPACE_MODELS = "http://schemas.datacontract.org/2004/07/WCFService.Models";
    private static final String NAMESPACE_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    private static final String PREFIX_TEMPURI = "tem";
    private static final String PREFIX_MODELS = "mod";
    private static final String CONVERSION_ENDPOINT = "http://localhost:5001/Conversion.svc";

    public ConversionResult conversionUnidades(double valor, String unidadOrigen) {
        double valorEnKg = toKilograms(valor, unidadOrigen);
        try {
            SOAPMessage request = buildRequest(valorEnKg);
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

    public double obtenerValorConvertido(ConversionResult resultado, String unidadDestino) {
        return switch (unidadDestino) {
            case "kilograms" -> defaultDouble(resultado.massKg());
            case "grams" -> defaultDouble(resultado.massG());
            case "pounds" -> defaultDouble(resultado.massLb());
            default -> throw new IllegalArgumentException("Unidad de destino no soportada: " + unidadDestino);
        };
    }

    private double toKilograms(double valor, String unidadOrigen) {
        return switch (unidadOrigen) {
            case "kilograms" -> valor;
            case "grams" -> valor / 1000.0;
            case "pounds" -> valor * 0.45359237;
            default -> throw new IllegalArgumentException("Unidad de origen no soportada: " + unidadOrigen);
        };
    }

    private SOAPMessage buildRequest(double massKg) throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        message.getMimeHeaders().addHeader("SOAPAction", "\"http://tempuri.org/IService/Convert\"");
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        envelope.addNamespaceDeclaration(PREFIX_TEMPURI, NAMESPACE_TEMPURI);
        envelope.addNamespaceDeclaration(PREFIX_MODELS, NAMESPACE_MODELS);

        SOAPBody body = envelope.getBody();
        SOAPElement convertElement = body.addChildElement("Convert", PREFIX_TEMPURI);
        SOAPElement requestElement = convertElement.addChildElement("request", PREFIX_TEMPURI);
        requestElement.addChildElement("MassKg", PREFIX_MODELS).addTextNode(Double.toString(massKg));

        message.saveChanges();
        return message;
    }

    private ConversionResult parseResponse(SOAPMessage response) throws SOAPException {
        SOAPBody body = response.getSOAPBody();
        SOAPElement responseElement = firstChild(body, new QName(NAMESPACE_TEMPURI, "ConvertResponse"));
        if (responseElement == null) {
            throw new SOAPException("Respuesta inesperada del servicio de conversion.");
        }

        SOAPElement resultElement = firstChild(responseElement, new QName(NAMESPACE_TEMPURI, "ConvertResult"));
        if (resultElement == null) {
            throw new SOAPException("El servicio no devolvio resultados de conversion.");
        }

        Double massKg = parseDouble(resultElement, new QName(NAMESPACE_MODELS, "MassKg"));
        Double massLb = parseDouble(resultElement, new QName(NAMESPACE_MODELS, "MassLb"));
        Double massG = parseDouble(resultElement, new QName(NAMESPACE_MODELS, "MassG"));
        Double latitudeDecimal = parseDouble(resultElement, new QName(NAMESPACE_MODELS, "LatitudeDecimal"));
        Double latitudeRadians = parseDouble(resultElement, new QName(NAMESPACE_MODELS, "LatitudeRadians"));
        CoordinateDms latitudeDms = parseCoordinate(resultElement, "LatitudeDMS");
        Double longitudeDecimal = parseDouble(resultElement, new QName(NAMESPACE_MODELS, "LongitudeDecimal"));
        Double longitudeRadians = parseDouble(resultElement, new QName(NAMESPACE_MODELS, "LongitudeRadians"));
        CoordinateDms longitudeDms = parseCoordinate(resultElement, "LongitudeDMS");

        return new ConversionResult(
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

    private CoordinateDms parseCoordinate(SOAPElement parent, String elementName) throws SOAPException {
        SOAPElement coordinateElement = firstChild(parent, new QName(NAMESPACE_MODELS, elementName));
        if (coordinateElement == null || isNil(coordinateElement)) {
            return null;
        }

        Integer degrees = parseInteger(coordinateElement, new QName(NAMESPACE_MODELS, "Degrees"));
        Integer minutes = parseInteger(coordinateElement, new QName(NAMESPACE_MODELS, "Minutes"));
        Double seconds = parseDouble(coordinateElement, new QName(NAMESPACE_MODELS, "Seconds"));
        String hemisphereText = text(coordinateElement, new QName(NAMESPACE_MODELS, "Hemisphere"));
        Character hemisphere = hemisphereText != null && !hemisphereText.isBlank()
            ? hemisphereText.charAt(0)
            : null;

        return new CoordinateDms(degrees, minutes, seconds, hemisphere);
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

    private Double parseDouble(SOAPElement parent, QName name) {
        String value = text(parent, name);
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInteger(SOAPElement parent, QName name) {
        String value = text(parent, name);
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String text(SOAPElement parent, QName name) {
        SOAPElement element = firstChild(parent, name);
        if (element == null || isNil(element)) {
            return null;
        }
        return element.getTextContent();
    }

    private boolean isNil(SOAPElement element) {
        String isNil = element.getAttributeNS(NAMESPACE_XSI, "nil");
        return "true".equalsIgnoreCase(isNil);
    }

    private double defaultDouble(Double value) {
        return value != null ? value : 0.0;
    }
}
