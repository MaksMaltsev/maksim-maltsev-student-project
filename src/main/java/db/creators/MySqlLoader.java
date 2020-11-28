package db.creators;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import parser.Member;

interface MySqlLoader{
    public List<Member> lazyLoad(String db, Member element, Connection connection) throws SQLException;
    public Member fullLoad(String db, Member element, Connection connection) throws SQLException;

}
