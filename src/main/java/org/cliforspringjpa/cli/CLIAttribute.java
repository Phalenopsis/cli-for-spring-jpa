package org.cliforspringjpa.cli;

import org.cliforspringjpa.domain.Attribute;
import org.cliforspringjpa.domain.Entity;
import org.cliforspringjpa.project.Project;
import org.cliforspringjpa.domain.Relationship;
import org.cliforspringjpa.exception.*;
import org.cliforspringjpa.utils.CaseManager;

import java.util.*;

public class CLIAttribute {
    private final Entity entity;

    public CLIAttribute(Entity pEntity) {
        entity = pEntity;
    }

    public Attribute askForAttribute() throws NoScannerException, ExitException, EndEntityException, EndOfActionException {
        String attributeName = askForAttributeName();
        if(entity.alreadyHasAttribute(attributeName)) {
            String response = "Entity " + entity.getName() + " has already an attribute named " + attributeName;
            System.out.println(response);
            return askForAttribute();
        }
        String attributeType = askForAttributeType();
        Attribute attribute = new Attribute(attributeName, attributeType);
        System.out.println(attributeName + " de type " + attributeType);
        if(Project.getInstance().getEntitiesList().contains(attributeType)) {
            askForRelationship(entity, attribute);
        }
        System.out.println(attribute);
        return attribute;
    }

    public String askForAttributeName() throws NoScannerException, ExitException, EndEntityException, EndOfActionException {
        String attributeNameQuestion = "\tWhat attribute do you want to add to the " + entity.getName() + " class ?";
        String toStop = "\tJust press Enter to finish adding attributes to " + entity.getName();
        System.out.println(attributeNameQuestion);
        System.out.println(toStop);

        String attributeName = CLIInput.getInstance().askOpenedQuestion();
        if(attributeName.isEmpty()) {
            throw new EndEntityException();
        }
        return attributeName;
    }

    public String askForAttributeType() throws NoScannerException, ExitException, EndOfActionException {
        String attributeTypeQuestion = "\t\tWhat is the type of this attribute ?";
        String attributeType;
        System.out.println(attributeTypeQuestion);
        Set<String> knownTypes = new HashSet<>();
        knownTypes.addAll(Project.getInstance().getEntitiesList());
        knownTypes.addAll(Project.getInstance().getBASIC_TYPES());

        attributeType = CLIInput.getInstance().askOpenedQuestionWithLimitedChoices(knownTypes);
        return attributeType;
    }

    protected void askForRelationship(Entity entity, Attribute attribute) throws NoScannerException, EndOfActionException, ExitException {
        String relationshipQuestion = "\t\tWhat relationship will rely "
                + entity.getName() + " with " + attribute.getName();
        String explanations = String.format("""
                \t\t1 - OneToOne\t => One %s have one %s
                \t\t2 - OneToMany\t => One %s could have many %s
                \t\t3 - ManyToOne\t => Many %s have to one %s
                \t\t4 - ManyToMany\t => Many %s could have many %s
                """,
                entity.getName(), attribute.getName(),
                entity.getName(), attribute.getName(),
                entity.getName(), attribute.getName(),
                entity.getName(), attribute.getName()
        );
        Set<String> possibleResponses = Set.of("1", "2", "3", "4");

        System.out.println(relationshipQuestion);
        System.out.println(explanations);
        String response = CLIInput.getInstance().askOpenedQuestionWithLimitedChoices(possibleResponses);
        try {
            setAttributeRelationship(attribute, response);
            askForTwoWayBinding(entity, attribute);
        } catch (RelationshipException e) {
            System.out.println(e);
        }
    }

    private void setAttributeRelationship(Attribute attribute, String userResponse) throws RelationshipException {
        switch (userResponse) {
            case "1":
                attribute.setRelationship(Relationship.ONE_TO_ONE);
                break;
            case "2":
                attribute.setRelationship(Relationship.ONE_TO_MANY);
                break;
            case "3":
                attribute.setRelationship(Relationship.MANY_TO_ONE);
                break;
            case "4":
                attribute.setRelationship(Relationship.MANY_TO_MANY);
                break;
            default:
                throw new RelationshipException(">>>> " + userResponse + "Attribute relationship is not recognize.");
        }
    }

    private void askForTwoWayBinding(Entity entity, Attribute attribute) throws NoScannerException, EndOfActionException, ExitException {
        String questionForTwoWayBinding = "\t\tWould you two way binding between " + entity.getName() + " and " + attribute.getType() + " ? (Y/n)";
        System.out.println(questionForTwoWayBinding);
        boolean response = CLIInput.getInstance().askBooleanQuestion();

        if(!response) return;
        if(attribute.getRelationship().equals(Relationship.ONE_TO_ONE)) {
            Project project = Project.getInstance();
            Entity attributeType = project.getEntity(attribute.getType());
            Attribute attributeInAttributeType = new Attribute(
                    CaseManager.switchToCamelCase(entity.getName()), entity.getName());
            attributeInAttributeType.setRelationship(Relationship.ONE_TO_ONE);
            attributeType.addAttribute(attributeInAttributeType);
        } else {
            askForMasterInRelationship(entity, attribute);
        }
    }

    private void askForMasterInRelationship(Entity entity, Attribute attribute) throws NoScannerException, EndOfActionException, ExitException {
        Project project = Project.getInstance();
        Entity attributeType = project.getEntity(attribute.getType());
        attributeType.setModified(true);
        String question = "\t\tWhich one will master relationship ? \n"
                + "\t\t1- " + entity.getName() + "\n"
                + "\t\t2- " + attributeType.getName() + "\n";
        System.out.println(question);

        Set<String> allowedResponses = Set.of("1", "2");
        String response = CLIInput.getInstance().askOpenedQuestionWithLimitedChoices(allowedResponses);

        attribute.setRelationshipMaster(response.equals("1"));
        Attribute attributeInAttributeType = new Attribute(
                CaseManager.switchToCamelCase(entity.getName()), entity.getName());
        if(attribute.getRelationship().equals(Relationship.ONE_TO_MANY)) {
            attributeInAttributeType.setRelationship(Relationship.MANY_TO_ONE);
        } else if(attribute.getRelationship().equals(Relationship.MANY_TO_ONE)) {
            attributeInAttributeType.setRelationship(Relationship.ONE_TO_MANY);
        } else {
            attributeInAttributeType.setRelationship(attribute.getRelationship());
        }
        attributeInAttributeType.setRelationshipMaster(!attribute.isRelationshipMaster());
        attributeType.addAttribute(attributeInAttributeType);
    }
}
