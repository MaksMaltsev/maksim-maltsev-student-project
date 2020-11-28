package db.creators;

import db.constants.mySQL.Queries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import parser.Member;

public class ForeignKeyCreator implements MySqlLoader {
    @Override
    public List<Member> lazyLoad(String db, Member foreignKeyFolder, Connection connection) throws SQLException {
        List<Member> keysList = new ArrayList<>();
        Member table = foreignKeyFolder.getParent();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.ALL_FOREIGN_KEY_INFO.get());
        preparedStatement.setString(1, db);
        preparedStatement.setString(2, table.getName());
        ResultSet resultSet = preparedStatement.executeQuery();
        LazyQueries lazyQueries = new LazyQueries();
        while (resultSet.next()) {
            Member key = lazyQueries.newMemberAndName(resultSet.getString(5));
            foreignKeyFolder.addChild(key);
            keysList.add(key);
        }
        return keysList;
    }

    @Override
    public Member fullLoad(String db, Member key, Connection connection) throws SQLException {
        String table = key.getParent().getParent().getName();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.ALL_FOREIGN_KEY_INFO.get());
        preparedStatement.setString(1, db);
        preparedStatement.setString(2, table);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (key.getName().equals(resultSet.getString(6))) {
                Map<String, String> attr = new HashMap<>();
                attr.put("column name", resultSet.getString(1));
                attr.put("schema name", resultSet.getString(2));
                attr.put("table name", resultSet.getString(4));
                attr.put("referenced table name", resultSet.getString(10));
                attr.put("referenced column name", resultSet.getString(11));
                key.setAttributes(attr);
            }
        }
        return key;
    }
}
