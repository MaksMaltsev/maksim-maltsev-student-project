package parser;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Convert from JavaObject.
 */

public class JavaToXML {
    /**
     * Convert from JavaObject in xml document.
     */
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

    /**
     * Sends an email.
     */
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
