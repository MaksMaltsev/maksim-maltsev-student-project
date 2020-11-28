package connection;

import db.creators.CreatorFactory;
import db.creators.TableFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBreader {
    String user = "root";
    String password = "program1";
    String url = "jdbc:mysql://127.0.0.1:3306/";
    String useSSL = "?useSSL=false";

    public void getMetaDataFromDB(String db) throws SQLException {
        DBProcessor dbProcessor = new DBProcessor();
        Connection connection = dbProcessor.getConnection(url + db + useSSL, user, password);
        Statement statement = connection.createStatement();
        this.createObjectFromDB(db, statement, connection);
    }

    public void createObjectFromDB(String db, Statement statement, Connection connection) throws SQLException {
        CreatorFactory creatorFactory = createMemberBySpecialty("table");
    }

    static CreatorFactory createMemberBySpecialty(String specialty) {
        if (specialty.equalsIgnoreCase("table")) {
            return new TableFactory();
        }
        return null;
    }
}
