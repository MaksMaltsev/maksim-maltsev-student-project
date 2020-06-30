package parser;

import java.util.ArrayList;

/**
 * List for saving children for Member.
 */

public class ChildrenList extends ArrayList<Member> {
    private Member parent;

    public ChildrenList(Member parent) {
        this.parent = parent;
    }
    /**
     * Method for saving children.
     */
    public boolean add(Member member) {
        if (member != null && member.getParent() == null) {
            member.setParent(this.parent);
            return super.add(member);
        } else {
            return false;
        }
    }
}
