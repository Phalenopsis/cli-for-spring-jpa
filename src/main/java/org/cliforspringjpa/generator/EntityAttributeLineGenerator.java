package org.cliforspringjpa.generator;

import org.cliforspringjpa.domain.Attribute;
import org.cliforspringjpa.domain.Entity;
import org.cliforspringjpa.domain.Relationship;
import org.cliforspringjpa.utils.CaseManager;

import java.util.ArrayList;
import java.util.List;

public class EntityAttributeLineGenerator {
    Entity entity;

    EntityAttributeLineGenerator(Entity pEntity) {
        entity = pEntity;
    }

    public List<String> generateAttribute(Attribute attribute) {
        if(attribute.getName().equals("id")) {
            return generateIdAttribute(attribute);
        }
        switch (attribute.getRelationship()) {
            case Relationship.NO_RELATION -> {
                return generateBasicAttribute(attribute);
            }
            case Relationship.ONE_TO_ONE -> {
                return generateOneToOneAttribute(attribute);
            }
            case Relationship.ONE_TO_MANY -> {
                return generateOneToManyAttribute(attribute);
            }
            case Relationship.MANY_TO_ONE -> {
                return generateManyToOneAttribute(attribute);
            }
            case Relationship.MANY_TO_MANY -> {
                return generateManyToManyAttribute(attribute);
            }
        }
        throw new RuntimeException("Error in EntityGenerator.generateAttribute");
    }

    private List<String> generateIdAttribute(Attribute attribute) {
        List<String> lines = new ArrayList<>();
        lines.add("\t@Id");
        lines.add("\t@GeneratedValue(strategy = GenerationType.IDENTITY)");
        lines.add(generateAttributeLine(attribute));
        return lines;
    }

    private String generateAttributeLine(Attribute attribute) {
        return "\tprivate " + attribute.getType() + " " + attribute.getName() + ";";
    }

    private List<String> generateBasicAttribute(Attribute attribute) {
        List<String> lines = new ArrayList<>();
        lines.add("\t@Column");
        lines.add(generateAttributeLine(attribute));
        return lines;
    }

    private List<String> generateOneToOneAttribute(Attribute attribute) {
        List<String> lines = new ArrayList<>();
        lines.add("\t@OneToOne");
        if(attribute.isRelationshipMaster()) {
            lines.add(
                    "\t@JoinColumn(name = \""
                            + attribute.getName()
                            + "_id\")"
            );
        } else {
            lines.addFirst(
                    lines.removeFirst()
                            + "(mappedBy = \""
                            + CaseManager.switchToKebabCase(entity.getName())
                            + "\")");
        }
        lines.add(generateAttributeLine(attribute));

        return lines;
    }

    private List<String> generateManyToOneAttribute(Attribute attribute) {
        List<String> lines = new ArrayList<>();
        lines.add("\t@ManyToOne");
        lines.add(
                "\t@JoinColumn(name = \""
                        + attribute.getName()
                        + "_id\")"
        );
        lines.add(generateAttributeLine(attribute));

        return lines;
    }

    private List<String> generateManyToManyAttribute(Attribute attribute) {
        List<String> lines = new ArrayList<>();
        lines.add("\t@ManyToMany");
        lines.add(generateManyAttributeLine(attribute));
        return lines;
    }

    private String generateManyAttributeLine(Attribute attribute) {
        return "\tprivate List<" + attribute.getType() + "> " + attribute.getName() + "s;";
    }

    private List<String> generateOneToManyAttribute(Attribute attribute) {
        List<String> lines = new ArrayList<>();
        lines.add(
                "\t@OneToMany(mappedBy = \""
                        + CaseManager.switchToKebabCase(entity.getName())
                        + "\")");
        lines.add(generateManyAttributeLine(attribute));
        return lines;
    }
}
