package org.cliforspringjpa.domain;

import java.util.HashSet;
import java.util.Set;

public class Entity {
    private final String name;
    private final Set<Attribute> attributes = new HashSet<>();

    public Entity(String pName) {
        name = pName;
        if(!Attribute.getBasicTypeList().contains(name)) {
            Attribute.addAttribute(name);
        }
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public String getName() {
        return name;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", set=" + attributes +
                '}';
    }
}
