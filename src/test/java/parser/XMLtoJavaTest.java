package parser;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class XMLtoJavaTest {
    @Test
    public void parserToJavaTest() throws IOException, SAXException, ParserConfigurationException, TransformerException {
        XMLtoJava xmLtoJava = new XMLtoJava();
        Member member = xmLtoJava.parserToJava("test.xml");
        JavaToXML javaToXML = new JavaToXML();
        javaToXML.saveInXML(member, "resultTest");
        List<String> links1 = Files.readAllLines(Paths.get("test.xml"), StandardCharsets.UTF_8);
        List<String> links2 = Files.readAllLines(Paths.get("resultTest.xml"), StandardCharsets.UTF_8);
        String expected = builder(links1).replaceAll(" ", "");
        String actual = builder(links2).replaceAll(" ", "");

        assertEquals(expected, actual);
    }
    private String builder(List<String> list){
        StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.length());
        for (String line : list) {
            sb.append(line);
        }
        return sb.toString();
    }


}