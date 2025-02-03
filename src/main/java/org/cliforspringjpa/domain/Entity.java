package org.cliforspringjpa.domain;

import java.util.HashSet;
import java.util.Set;

public class Entity {
    private final String name;
    private final Set<Attribute> set = new HashSet<>();

    public Entity(String pName) {
        name = pName;
        if(!Attribute.getBasicTypeList().contains(name)) {
            Attribute.addAttribute(name);
        }
    }

    public void addAttribute(Attribute attribute) {
        set.add(attribute);
    }

    public String getName() {
        return name;
    }

    public Set<Attribute> getSet() {
        return set;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", set=" + set +
                '}';
    }
}
