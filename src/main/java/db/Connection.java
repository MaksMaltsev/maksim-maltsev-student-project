package db;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    String user = "root";
    String password = "program1";
    String url = "jdbc:mysql://localhost:3306/project_schema";

    public void getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        try (java.sql.Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("We are connected");
        }
    }
}
