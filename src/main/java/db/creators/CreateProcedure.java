package db.creators;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import parser.Member;

public class CreateProcedure implements MySqlLoader{
    @Override
    public List<Member> lazyLoad(String db, Member procedureFolder, Connection connection) throws SQLException {
        return null;
    }

    @Override
    public Member fullLoad(String db, Member element, Connection connection) throws SQLException {
        return null;
    }
}
