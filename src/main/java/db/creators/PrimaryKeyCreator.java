package db.creators;

import db.constants.mySQL.Queries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import parser.Member;

public class PrimaryKeyCreator implements MySqlLoader {
    public Member getPrimaryKeyName(String db, Member table, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.PRIMARY_KEY_LAZY_LOAD.get());
        preparedStatement.setString(1, db);
        preparedStatement.setString(2, table.getName());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Member key = new Member();
            key.setName(resultSet.getString(1));
            table.getChildrenList().get(2).addChild(key);
            return key;
        }
        return table.getChildrenList().get(1);
    }
    public Member getPrimaryKey(String db, Member table, Member key, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.ALL_PRIMARY_KEY_INFO.get());
        preparedStatement.setString(1, db);
        preparedStatement.setString(2, table.getName());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Map<String, String> attr = new HashMap<>();
            attr.put("column name", resultSet.getString(11));
            attr.put("schema name", resultSet.getString(2));
            attr.put("table name", resultSet.getString(3));
            key.setName(resultSet.getString(11));
            key.setAttributes(attr);
        }
        return key;
    }

    @Override
    public List<Member> lazyLoad(String db, Member primaryKeyFolder, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.PRIMARY_KEY_LAZY_LOAD.get());
        String table = primaryKeyFolder.getParent().getName();
        preparedStatement.setString(1, db);
        preparedStatement.setString(2, table);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Member> pkeyList = new ArrayList<>();
        while (resultSet.next()) {
            Member key = new Member();
            key.setName(resultSet.getString(1));
            primaryKeyFolder.addChild(key);
        }
        return pkeyList;
    }

    @Override
    public Member fullLoad(String db, Member key, Connection connection) throws SQLException {
        String table = key.getParent().getParent().getName();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.ALL_PRIMARY_KEY_INFO.get());
        preparedStatement.setString(1, db);
        preparedStatement.setString(2, table);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if(key.getName().equals(resultSet.getString(11))) {
                Map<String, String> attr = new HashMap<>();
                attr.put("column name", resultSet.getString(11));
                attr.put("schema name", resultSet.getString(2));
                attr.put("table name", resultSet.getString(3));
                key.setAttributes(attr);
            }
        }
        return key;
    }
}
