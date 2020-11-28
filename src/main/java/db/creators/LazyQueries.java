package db.creators;

import java.util.List;
import parser.Member;

public class LazyQueries {
     public Member newMemberAndName(String name){
         Member member = new Member();
         member.setName(name);
         return member;
     }
}
