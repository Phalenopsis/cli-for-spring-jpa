package org.cliforspringjpa.domain;

import org.cliforspringjpa.utils.CaseManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Attribute {
    private static final Set<String> basicTypeList = new HashSet<>(List.of(new String[]{"Long", "int", "String"}));
    private static final Set<String> newTypeList = new HashSet<>();
    private final String name;
    private final String pascalCaseName;
    private final String type;
    private Relationship relationship = Relationship.NO_RELATION;
    private boolean relationshipMaster;

    public Attribute(String pName, String pType) {
        name = pName;
        type = pType;
        pascalCaseName = CaseManager.switchToPascalCase(pName);
    }

    public static void addAttribute(String attribute) {
        newTypeList.add(attribute);
    }

    public static Set<String> getBasicTypeList() {
        return basicTypeList;
    }

    public static Set<String> getNewTypeList() {
        return newTypeList;
    }

    public String getName() {
        return name;
    }

    public String getPascalCaseName() {
        return pascalCaseName;
    }

    public String getType() {
        return type;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        String base = name + " => " + type;
        if (relationship.equals(Relationship.NO_RELATION)) {
            base = base + " " + relationship;
            base = base + " " + (relationshipMaster ? "is master" : "is slave");
        }
        return base;
    }

    public boolean isRelationshipMaster() {
        return relationshipMaster;
    }

    public void setRelationshipMaster(boolean relationshipMaster) {
        this.relationshipMaster = relationshipMaster;
    }

}
