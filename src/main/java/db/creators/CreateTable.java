package db.creators;

import db.constants.mySQL.Queries;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import parser.Member;

public class CreateTable implements MySqlLoader{
    @Override
    public List<Member> lazyLoad(String db, Member tablesFolder, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Queries.ALL_TABLES.get());
        List<Member> list = new ArrayList<>();
        LazyQueries lazyQueries = new LazyQueries();
        while (resultSet.next()) {
            Member table = lazyQueries.newMemberAndName(resultSet.getString(1));
            tablesFolder.addChild(table);
            list.add(table);
        }
        return list;
    }

    @Override
    public Member fullLoad(String db, Member table, Connection connection) {
        LazyQueries lazyQueries = new LazyQueries();
        Member columns = lazyQueries.newMemberAndName("columns");
        Member foreignKey = lazyQueries.newMemberAndName("foreign key");
        Member index = lazyQueries.newMemberAndName("index");
        Member primaryKey = lazyQueries.newMemberAndName("primary key");
        Member trigger = lazyQueries.newMemberAndName("trigger");
        table.addChild(columns);
        table.addChild(foreignKey);
        table.addChild(primaryKey);
        table.addChild(index);
        table.addChild(trigger);
        return table;
    }
}
