package parser;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Convert java object from xml document.
 */
public class XMLtoJava {
    /**
     * Convert java object from xml document.
     */
    public Member parserToJava(String xmlFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(xmlFile));
        Element element = document.getDocumentElement();
        return makeMember(element);
    }

    private Member makeMember(Element element) {
        Member member = new Member();
        member.setName(element.getTagName());
        if (element.hasAttributes()) {
            member.setAttributes(getAttributes(element));
        }
        if (element.hasChildNodes()) {
            NodeList children = element.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                if (children.item(i) instanceof Element) {
                    Element child = (Element) children.item(i);
                    Member childTag = makeMember(child);
                    member.addChild(childTag);
                }
            }
        }
        if (this.hasText(element)) {
            member.setContent(element.getTextContent().trim());
        }
        return member;
    }

    private boolean hasText(Element element) {
        boolean result = false;
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.TEXT_NODE) {
                String content = node.getNodeValue().trim();
                if (content.length() > 0) {
                    result = true;
                }
            }
        }
        return result;
    }

    private Map<String, String> getAttributes(Element element) {
        NamedNodeMap map = element.getAttributes();
        Map<String, String> attributes = new HashMap();
        for (int i = 0; i < map.getLength(); ++i) {
            attributes.put(map.item(i).getNodeName(), map.item(i).getNodeValue());
        }
        return attributes;
    }
}

