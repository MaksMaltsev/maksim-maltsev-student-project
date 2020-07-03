package parser;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import junit.framework.TestCase;
import org.junit.Test;
import org.xml.sax.SAXException;

public class MemberTest extends TestCase {

    @Test
    public void testSearchInDeep() throws IOException, SAXException, ParserConfigurationException {
        XMLtoJava xmLtoJava = new XMLtoJava();
        Member member = xmLtoJava.parserToJava("restaurant.xml");
        List<Member> listActual = member.searchInDeep("Monday");
        assertEquals(1, listActual.size());
    }

    @Test
    public void testSearchInWidth() throws IOException, SAXException, ParserConfigurationException {
        XMLtoJava xmLtoJava = new XMLtoJava();
        Member member = xmLtoJava.parserToJava("restaurant.xml");
        List<Member> listActual = member.searchInWidth("Monday");
        assertEquals(1, listActual.size());
    }
}