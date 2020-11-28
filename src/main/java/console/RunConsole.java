package console;


import connection.DBreader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;

public class RunConsole {
    public static void main(String[] args)
            throws TransformerException, IOException, SAXException, ParserConfigurationException, SQLException, ClassNotFoundException {
        DBreader dBreader = new DBreader();
        dBreader.getMetaDataFromDB("mydb");
    }
}
