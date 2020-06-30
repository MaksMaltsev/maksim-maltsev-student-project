package parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import navigation.Searchable;

/**
 * Example of an object when converting from xml document.
 */
public class Member implements Searchable {
    private Member parent;
    private String name;
    private Map<String, String> attributes = new LinkedHashMap();
    private List<Member> childrenList = new ChildrenList(this);
    private String content = "";


    public Member getParent() {
        return this.parent;
    }

    void setParent(Member parent) {
        this.parent = parent;
    }

    public void setAttribute(String key, String value) {
        this.attributes.put(key, value);
    }

    public String getAttributeByKey(String key) {
        return (String) this.attributes.get(key);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<Member> getChildrenList() {
        return this.childrenList;
    }

    private boolean hasChildren() {
        boolean result = false;
        if (this.getChildrenList().size() > 0) {
            result = true;
        }
        return result;
    }

    public void addChild(Member child) {
        this.childrenList.add(child);
    }

    @Override
    public List<Member> searchInDeep(String text) {
        List<Member> list = new ArrayList<>();
        if (searchMatches(text) != null) {
            list.add(this);
        }
        if (hasChildren()) {
            List<Member> childrenList = this.getChildrenList();
            for (Member member : childrenList) {
                List<Member> listForResult = member.searchInDeep(text);
                list.addAll(listForResult);
            }
        }
        return list;
    }

    @Override
    public List<Member> searchInWidth(String text) {
        List<Member> listResult = new ArrayList<>();
        List<Member> listForChild = this.getChildrenList();
        List<Member> secondListForChild = new ArrayList<>();
        if (searchMatches(text) != null) {
            listResult.add(this);
        }
        while (listForChild.size() > 0) {
            for (Member member : listForChild) {
                if (member.searchMatches(text) != null) {
                    listResult.add(member);
                }
                secondListForChild.addAll(member.getChildrenList());
            }
            listForChild.clear();
            listForChild.addAll(secondListForChild);
            secondListForChild.clear();
        }
        return listResult;
    }

    private Member searchMatches(String text) {
        if (this.getName().contains(text)) {
            return this;
        }
        if (this.getAttributes().size() > 0) {
            Set<String> keys = getAttributes().keySet();
            Map<String, String> map = getAttributes();
            for (String attribute : keys) {
                String content = attributes.get(attribute);
                if (content.contains(text)) {
                    return this;
                }
            }
        }
        if (this.getContent().contains(text)) {
            return this;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\n<").append(this.name.equals("") ? "UNNAMED" : this.name).append(">:");
        if (!this.getAttributes().isEmpty()) {
            result.append(" attr: ").append(this.attributes);
        }

        if (!this.getChildrenList().isEmpty()) {
            result.append(" children: ").append(this.getChildrenList());
        }

        if (!this.getContent().equals("")) {
            result.append(" content: ").append(this.content);
        }

        if (this.parent == null) {
            result.append(" parent: [").append("document").append("]");
        } else {
            result.append(" parent: [").append(this.parent.name).append("]");
        }

        result.append("<").append(this.name.equals("") ? "UNNAMED" : this.name).append(">");
        return result.toString();
    }
}
