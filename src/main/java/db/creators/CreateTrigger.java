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

public class CreateTrigger implements MySqlLoader {
    @Override
    public List<Member> lazyLoad(String db, Member table, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.TRIGGERS_LAZY_LOAD.get());
        preparedStatement.setString(1, db);
        preparedStatement.setString(2, table.getName());
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Member> triggers = new ArrayList<>();
        while (resultSet.next()) {
            Member trigger = new Member();
            trigger.setName(resultSet.getString(1));
            table.getChildrenList().get(4).addChild(trigger);
            triggers.add(trigger);
        }
        return triggers;
    }

    @Override
    public Member fullLoad(String db, Member trigger, Connection connection) throws SQLException {
        Member table = trigger.getParent().getParent();
        PreparedStatement preparedStatement = connection.prepareStatement(Queries.ALL_TRIGGERS_INFO.get());
        preparedStatement.setString(1, db);
        preparedStatement.setString(2, table.getName());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (trigger.getName().equals(resultSet.getString(3))) {
                Map<String, String> attr = new HashMap<>();
                attr.put("event manipulation", resultSet.getString(4));
                attr.put("schema name", resultSet.getString(2));
                attr.put("table name", resultSet.getString(7));
                trigger.setAttributes(attr);
            }
        }
        return trigger;
    }
}
