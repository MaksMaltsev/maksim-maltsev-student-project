package parser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Member {
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
        return (String)this.attributes.get(key);
    }

    public void setName(String name) { this.name = name; }

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

    public void addChild(Member child) {
        this.childrenList.add(child);
    }
}
