package db.creators;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import parser.Member;

public class CreateDBObject implements MySqlLoader{
    @Override
    public List<Member> lazyLoad(String db, Member element, Connection connection) throws SQLException {
        List<Member> list = new ArrayList<>();
        Member dataBase = new Member();
        dataBase.setName(db);
        list.add(dataBase);
        return list;
    }

    @Override
    public Member fullLoad(String db, Member element, Connection connection) {
        LazyQueries lazyQueries = new LazyQueries();
        Member tables = lazyQueries.newMemberAndName("Tables");
        Member views = lazyQueries.newMemberAndName("Views");
        Member storedProcedures = lazyQueries.newMemberAndName("Stored Procedures");
        Member functions = lazyQueries.newMemberAndName("Functions");
        element.addChild(tables);
        element.addChild(views);
        element.addChild(storedProcedures);
        element.addChild(functions);
        return element;
    }
}
