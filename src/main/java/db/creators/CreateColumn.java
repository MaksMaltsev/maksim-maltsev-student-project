package db.creators;

import db.constants.mySQL.Queries;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import parser.Member;

public class CreateColumn implements MySqlLoader {
    @Override
    public List<Member> lazyLoad(String db, Member table, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        List<Member> allColumnsList = new ArrayList<>();
        LazyQueries lazyQueries = new LazyQueries();
        Member columnFolder = table.getChildrenList().get(0);
        ResultSet resultSet = statement.executeQuery(Queries.ALL_COLUMNS.get() + "'" + table.getName() + "'");
        while (resultSet.next()) {
            Member columnsName = lazyQueries.newMemberAndName(resultSet.getString(1));
            columnFolder.addChild(columnsName);
            allColumnsList.add(columnsName);
        }
        return allColumnsList;
    }

    @Override
    public Member fullLoad(String db, Member column, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        Map<String, String> map = new HashMap<>();
        Member table = column.getParent().getParent();
        ResultSet resultSet = statement.executeQuery(Queries.COLUMN_INFO.get() + table.getName());
        while (resultSet.next()) {
            if (column.getName().equals(resultSet.getString(1))) {
                map.put("type", resultSet.getString(2));
            }
        }
        column.setAttributes(map);
        return column;
    }
}
