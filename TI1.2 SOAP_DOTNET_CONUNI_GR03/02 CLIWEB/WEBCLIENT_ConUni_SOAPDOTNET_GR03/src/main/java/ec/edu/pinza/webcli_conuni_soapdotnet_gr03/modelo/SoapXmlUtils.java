package ec.edu.pinza.webcli_conuni_soapdotnet_gr03.modelo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

final class SoapXmlUtils {

    private static final String NIL_NS = "http://www.w3.org/2001/XMLSchema-instance";
    private static final String NIL_ATTR = "nil";

    private SoapXmlUtils() {
    }

    static Document parseXml(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        try (ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))) {
            return builder.parse(input);
        }
    }

    static Element findFirstChild(Element parent, String localName) {
        if (parent == null) {
            return null;
        }
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node instanceof Element element) {
                String candidate = Optional.ofNullable(element.getLocalName()).orElse(element.getNodeName());
                if (localName.equals(candidate)) {
                    return element;
                }
            }
        }
        return null;
    }

    static String getChildText(Element parent, String localName) {
        Element child = findFirstChild(parent, localName);
        if (child == null || isNil(child)) {
            return null;
        }
        String text = child.getTextContent();
        return text != null ? text.trim() : null;
    }

    static Double getChildDouble(Element parent, String localName) {
        String text = getChildText(parent, localName);
        if (text == null || text.isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    static Integer getChildInteger(Element parent, String localName) {
        String text = getChildText(parent, localName);
        if (text == null || text.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    static Boolean getChildBoolean(Element parent, String localName) {
        String text = getChildText(parent, localName);
        if (text == null || text.isEmpty()) {
            return null;
        }
        return Boolean.parseBoolean(text);
    }

    static boolean isNil(Element element) {
        if (element == null) {
            return true;
        }
        if (!element.hasAttributes()) {
            return false;
        }
        for (int i = 0; i < element.getAttributes().getLength(); i++) {
            Node attrNode = element.getAttributes().item(i);
            if (attrNode instanceof Attr attr) {
                String local = Optional.ofNullable(attr.getLocalName()).orElse(attr.getName());
                String namespace = attr.getNamespaceURI();
                if (NIL_ATTR.equals(local) && NIL_NS.equals(namespace)) {
                    return "true".equalsIgnoreCase(attr.getValue());
                }
            }
        }
        return false;
    }

    static String escapeXml(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}

