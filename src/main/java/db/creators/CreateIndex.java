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

public class CreateIndex implements MySqlLoader {
    @Override
    public List<Member> lazyLoad(String db, Member table, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.INDEX_LAZY_LOAD.get());
        preparedStatement.setString(1, db);
        preparedStatement.setString(2, table.getName());
        ResultSet resultSet = preparedStatement.executeQuery();
        Member indexFolder = table.getChildrenList().get(3);
        List<Member> indexList = new ArrayList<>();
        while (resultSet.next()) {
            Member index = new Member();
            index.setName(resultSet.getString(1));
            indexFolder.addChild(index);
            indexList.add(index);
        }
        return indexList;
    }

    @Override
    public Member fullLoad(String db, Member index, Connection connection) throws SQLException {
        Member table = index.getParent().getParent();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.ALL_INDEX_INFO.get());
        preparedStatement.setString(1, db);
        preparedStatement.setString(2, table.getName());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (index.getName().equals(resultSet.getString(6))) {
                Map<String, String> attr = new HashMap<>();
                attr.put("column name", resultSet.getString(8));
                attr.put("schema name", resultSet.getString(2));
                attr.put("table name", resultSet.getString(3));
                index.setAttributes(attr);
            }
        }
        return index;
    }
}
