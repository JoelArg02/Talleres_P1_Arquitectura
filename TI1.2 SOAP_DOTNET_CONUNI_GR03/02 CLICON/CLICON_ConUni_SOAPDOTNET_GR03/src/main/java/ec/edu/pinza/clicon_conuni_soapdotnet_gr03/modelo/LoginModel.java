package ec.edu.pinza.clicon_conuni_soapdotnet_gr03.modelo;

import ec.edu.pinza.clicon_conuni_soapdotnet_gr03.modelo.dto.LoginResult;
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

public class LoginModel {

    private static final String NAMESPACE_TEMPURI = "http://tempuri.org/";
    private static final String NAMESPACE_MODELS = "http://schemas.datacontract.org/2004/07/WCFService.Models";
    private static final String NAMESPACE_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    private static final String LOGIN_ENDPOINT = "http://localhost:5001/Login.svc";
    private static final String PREFIX_TEMPURI = "tem";
    private static final String PREFIX_MODELS = "mod";

    public LoginResult autenticar(String usuario, String contrasena) {
        try {
            SOAPMessage request = buildRequest(usuario, contrasena);
            SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = connectionFactory.createConnection();
            try {
                SOAPMessage response = connection.call(request, LOGIN_ENDPOINT);
                return parseResponse(response);
            } finally {
                connection.close();
            }
        } catch (Exception e) {
            System.err.println("Error al autenticar con el servicio SOAP: " + e.getMessage());
            return LoginResult.failure("No se pudo contactar el servicio de autenticacion.");
        }
    }

    private SOAPMessage buildRequest(String usuario, String contrasena) throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        message.getMimeHeaders().addHeader("SOAPAction", "\"http://tempuri.org/ILoginService/ValidarCredenciales\"");
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        envelope.addNamespaceDeclaration(PREFIX_TEMPURI, NAMESPACE_TEMPURI);
        envelope.addNamespaceDeclaration(PREFIX_MODELS, NAMESPACE_MODELS);

        SOAPBody body = envelope.getBody();
        SOAPElement validarCredenciales = body.addChildElement("ValidarCredenciales", PREFIX_TEMPURI);
        validarCredenciales.addChildElement("username", PREFIX_TEMPURI).addTextNode(usuario);
        validarCredenciales.addChildElement("password", PREFIX_TEMPURI).addTextNode(contrasena);

        message.saveChanges();
        return message;
    }

    private LoginResult parseResponse(SOAPMessage response) throws SOAPException {
        SOAPBody body = response.getSOAPBody();
        if (body.hasFault()) {
            return LoginResult.failure(body.getFault().getFaultString());
        }

        SOAPElement responseElement = firstChildLocal(body, "ValidarCredencialesResponse");
        if (responseElement == null) {
            return LoginResult.failure("Respuesta inesperada del servicio de autenticacion.");
        }

        SOAPElement resultElement = firstChildLocal(responseElement, "ValidarCredencialesResult");
        if (resultElement == null) {
            return LoginResult.failure("El servicio no devolvio datos de autenticacion.");
        }

        String message = text(resultElement, "Message");
        String successValue = text(resultElement, "Success");
        String token = text(resultElement, "Token");

        boolean success = Boolean.parseBoolean(successValue);
        if (success) {
            String resolvedMessage = message != null && !message.isBlank()
                ? message
                : "Autenticacion exitosa.";
            return LoginResult.success(resolvedMessage, token);
        }

        String resolvedMessage = message != null && !message.isBlank()
            ? message
            : "Credenciales invalidas.";
        return LoginResult.failure(resolvedMessage);
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

    private SOAPElement firstChildLocal(SOAPElement parent, String localName) {
        Iterator<?> iterator = parent.getChildElements();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next instanceof SOAPElement element
                && localName.equals(element.getElementQName().getLocalPart())) {
                return element;
            }
        }
        return null;
    }

    private String text(SOAPElement parent, QName name) {
        SOAPElement element = firstChild(parent, name);
        if (element == null) {
            return null;
        }
        String isNil = element.getAttributeNS(NAMESPACE_XSI, "nil");
        if ("true".equalsIgnoreCase(isNil)) {
            return null;
        }
        return element.getTextContent();
    }

    private String text(SOAPElement parent, String localName) {
        SOAPElement element = firstChildLocal(parent, localName);
        if (element == null) {
            return null;
        }
        String isNil = element.getAttributeNS(NAMESPACE_XSI, "nil");
        if ("true".equalsIgnoreCase(isNil)) {
            return null;
        }
        return element.getTextContent();
    }
}
