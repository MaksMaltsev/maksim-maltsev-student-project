package console;


import org.xml.sax.SAXException;
import parser.JavaToXML;
import parser.Member;
import parser.XMLtoJava;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Scanner;

public class RunConsole {
    public static void main(String[] args) throws TransformerException, IOException, SAXException, ParserConfigurationException {
        Scanner in = new Scanner(System.in);
        System.out.print("Input xml name: ");
        String xmlFile = in.nextLine();
        XMLtoJava xmLtoJava = new XMLtoJava();
        Member member = xmLtoJava.parserToJava(xmlFile);
        System.out.print("Input xml output name: ");
        String name = in.nextLine();
        JavaToXML javaToXML = new JavaToXML();
        javaToXML.saveInXML(member, name);


    }
}
