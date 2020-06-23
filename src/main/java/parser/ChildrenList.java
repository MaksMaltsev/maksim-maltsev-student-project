package parser;

import java.util.ArrayList;

public class ChildrenList extends ArrayList<Member> {
    private Member parent;

    public ChildrenList(Member parent) {
        this.parent = parent;
    }

    public boolean add(Member member) {
        if (member != null && member.getParent() == null) {
            member.setParent(this.parent);
            return super.add(member);
        } else {
            return false;
        }
    }
}
