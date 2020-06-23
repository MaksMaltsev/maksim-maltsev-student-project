package parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class JavaToXML {
    public void saveInXML(Member member, String name) throws TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        assert builder != null;
        doc = builder.newDocument();
        doc.setXmlStandalone(true);

        Element firstElement = this.makeTag(doc, member);
        doc.appendChild(firstElement);
        this.writeDocument(doc, name);
    }

    private Element makeTag(Document document, Member member) {
        Element element = document.createElement(member.getName());
        if (member.getAttributes().size() > 0) {
            for (String key : member.getAttributes().keySet()) {
                element.setAttribute(key, member.getAttributeByKey(key));
            }
        }
        if (member.getContent().length() > 0) {
            element.setTextContent(member.getContent());
        }
        if (member.getChildrenList().size() > 0) {

            for (int i = 0; i < member.getChildrenList().size(); i++) {
                Element child = this.makeTag(document, member.getChildrenList().get(i));
                element.appendChild(child);

            }
        }



        return element;
    }

    private void writeDocument(Document document, String path)
            throws TransformerFactoryConfigurationError, TransformerException {
        Source domSource = new DOMSource(document);
        Result fileResult = new StreamResult(new File(path + ".xml"));
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(domSource, fileResult);
    }


}
