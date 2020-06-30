package parser;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class XMLtoJavaTest {
    @Test
    public void parserToJavaTest() throws IOException, SAXException, ParserConfigurationException {
        Member expected = getMember();
        XMLtoJava xmLtoJava = new XMLtoJava();
        Member actual = xmLtoJava.parserToJava("test.xml");
        System.out.println(expected.toString() + "\n" + actual.toString());
        assertEquals(expected.toString(), actual.toString());
    }

    private Member getMember() {
        Member expected = new Member();
        Member childFood = new Member();
        Member childName = new Member();
        Member childDescription = new Member();

        childFood.addChild(childName);
        childFood.addChild(childDescription);
        Map<String, String> attributes = new HashMap();
        attributes.put("days", "Monday, Friday");
        expected.setAttributes(attributes);
        expected.setName("breakfast_menu");
        childFood.setName("food");
        expected.addChild(childFood);
        childFood.setParent(expected);
        childDescription.setName("description");
        childName.setName("name");
        childName.setContent("Belgian Waffles");
        childDescription.setContent("Two of our famous Belgian Waffles with plenty of real maple syrup");
        return expected;
    }

    @Test(expected = IOException.class)
    public void parserToJavaTestCatch() throws IOException, SAXException, ParserConfigurationException {
        XMLtoJava xmLtoJava = new XMLtoJava();
        Member actual = xmLtoJava.parserToJava("no.xml");
    }


}