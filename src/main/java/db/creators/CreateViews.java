package db.creators;

import db.constants.mySQL.Queries;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import parser.Member;

public class CreateViews implements MySqlLoader {
    @Override
    public List<Member> lazyLoad(String db, Member viewsFolder, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(Queries.ALL_VIEWS.get());
        List<Member> viewsList = new ArrayList<>();
        LazyQueries lazyQueries = new LazyQueries();
        while (resultSet.next()) {
            Member view = lazyQueries.newMemberAndName(resultSet.getString(1));
            viewsList.add(view);
            viewsFolder.addChild(view);
        }
        return viewsList;
    }

    @Override
    public Member fullLoad(String db, Member view, Connection connection) throws SQLException {
        LazyQueries lazyQueries = new LazyQueries();
        Member columns = lazyQueries.newMemberAndName("columns");
        Member foreignKey = lazyQueries.newMemberAndName("foreign key");
        Member index = lazyQueries.newMemberAndName("index");
        Member primaryKey = lazyQueries.newMemberAndName("primary key");
        Member trigger = lazyQueries.newMemberAndName("trigger");
        view.addChild(columns);
        view.addChild(foreignKey);
        view.addChild(primaryKey);
        view.addChild(index);
        view.addChild(trigger);
        return view;
    }
}
