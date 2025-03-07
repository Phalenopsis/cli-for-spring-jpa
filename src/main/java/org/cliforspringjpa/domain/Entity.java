package org.cliforspringjpa.domain;

import org.cliforspringjpa.exception.IdNotFoundException;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Entity {
    private final String name;
    private final LinkedHashSet<Attribute> attributes = new LinkedHashSet<>();
    private boolean isModified = false;

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

    public LinkedHashSet<Attribute> getAttributes() {
        return attributes;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public boolean alreadyHasAttribute(String pAttributeName) {
        for(Attribute attribute : attributes) {
            if(attribute.getName().equals(pAttributeName)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", set=" + attributes +
                '}';
    }

    public String getIdType() throws IdNotFoundException {
        for(Attribute attribute: attributes) {
            if(attribute.getName().equals("id")) {
                return attributes.getFirst().getType();
            }
        }
        throw new IdNotFoundException(name);
    }
}
