package org.cliforspringjpa.generator;

import org.cliforspringjpa.domain.*;
import org.cliforspringjpa.exception.SpringProjectException;
import org.cliforspringjpa.project.Project;
import org.cliforspringjpa.project.ProjectPath;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.List;

public class TestEntityGenerator {

    @Test
    public void testGenerateIdAttribute() throws SpringProjectException {
        Entity book = new Entity("Book");
        Attribute id = new Attribute("id", "Long");
        book.addAttribute(id);
        EntityGenerator generator = new EntityGenerator(book);
        generator.generateLines();
        HashMap<String, List<String>> lines = generator.getAttributes();

        List<String> idLines = lines.get("id");

        Assertions.assertEquals("\t@Id", idLines.getFirst());
        Assertions.assertEquals("\t@GeneratedValue(strategy = GenerationType.IDENTITY)", idLines.get(1));
        Assertions.assertEquals("\tprivate Long id;", idLines.get(2));
    }

    @Test
    public void testGenerateBasicAttribute() throws SpringProjectException {
        Entity book = new Entity("Book");
        Attribute price = new Attribute("price", "double");
        book.addAttribute(price);
        EntityGenerator generator = new EntityGenerator(book);
        generator.generateLines();
        HashMap<String, List<String>> lines = generator.getAttributes();

        List<String> priceLines = lines.get("price");

        Assertions.assertEquals("\t@Column", priceLines.getFirst());
        Assertions.assertEquals("\tprivate double price;", priceLines.get(1));
    }

    @Test
    public void testGenerateOneToOneAttribute_MasterRelationship() throws SpringProjectException {
        Project.getInstance().reset();
        Entity image = new Entity("Image");
        Entity book = new Entity("Book");
        Attribute cover = new Attribute("cover", "Image");
        cover.setRelationship(Relationship.ONE_TO_ONE);
        cover.setRelationshipMaster(true);
        book.addAttribute(cover);
        EntityGenerator generator = new EntityGenerator(book);
        EntityGenerator imageGenerator = new EntityGenerator(image);
        Project.getInstance().addGenerator(generator);
        Project.getInstance().addGenerator(imageGenerator);
        generator.generateLines();
        HashMap<String, List<String>> lines = generator.getAttributes();

        List<String> coverLines = lines.get("cover");

        Assertions.assertEquals("\t@OneToOne", coverLines.getFirst());
        Assertions.assertEquals("\t@JoinColumn(name = \"cover_id\")", coverLines.get(1));
        Assertions.assertEquals("\tprivate Image cover;", coverLines.get(2));
    }

    @Test
    public void testGenerateOneToOneAttribute_SlaveRelationship() throws SpringProjectException {
        Project.getInstance().reset();
        Entity bookEntity = new Entity("Book");
        Entity image = new Entity("Image");
        Attribute book = new Attribute("book", "Book");
        book.setRelationship(Relationship.ONE_TO_ONE);
        book.setRelationshipMaster(false);
        image.addAttribute(book);
        EntityGenerator generator = new EntityGenerator(image);
        EntityGenerator bookGenerator = new EntityGenerator(bookEntity);
        Project.getInstance().addGenerator(generator);
        Project.getInstance().addGenerator(bookGenerator);
        generator.generateLines();
        HashMap<String, List<String>> lines = generator.getAttributes();

        List<String> bookLines = lines.get("book");

        Assertions.assertEquals("\t@OneToOne(mappedBy = \"image\")", bookLines.getFirst());
        Assertions.assertEquals("\tprivate Book book;", bookLines.get(1));
    }

    @Test
    public void testGenerateManyToOneAttribute() throws SpringProjectException {
        Entity authorEntity = new Entity("Author");
        Entity book = new Entity("Book");
         Attribute author = new Attribute("author", "Author");
         author.setRelationship(Relationship.MANY_TO_ONE);
         book.addAttribute(author);
         EntityGenerator generator = new EntityGenerator(book);
         EntityGenerator authorGenerator = new EntityGenerator(authorEntity);
         Project.getInstance().addGenerator(generator);
         Project.getInstance().addGenerator(authorGenerator);
         generator.generateLines();
         HashMap<String, List<String>> lines = generator.getAttributes();
         List<String> authorLine = lines.get("author");

         Assertions.assertEquals("\t@ManyToOne", authorLine.getFirst());
         Assertions.assertEquals("\t@JoinColumn(name = \"author_id\")", authorLine.get(1));
         Assertions.assertEquals("\tprivate Author author;", authorLine.get(2));
    }

    @Test
    public void testGenerateManyToManyAttribute() throws SpringProjectException {
        Entity bookEntity = new Entity("Book");
        Entity houseEditor = new Entity("HouseEditor");
        Attribute book = new Attribute("book", "Book");
        book.setRelationship(Relationship.MANY_TO_MANY);
        houseEditor.addAttribute(book);

        EntityGenerator bookGenerator = new EntityGenerator(bookEntity);
        EntityGenerator generator = new EntityGenerator(houseEditor);
        Project.getInstance().addGenerator(bookGenerator);
        Project.getInstance().addGenerator(generator);
        generator.generateLines();
        HashMap<String, List<String>> lines = generator.getAttributes();
        List<String> bookLines = lines.get("book");

        Assertions.assertEquals("\t@ManyToMany", bookLines.getFirst());
        Assertions.assertEquals("\tprivate List<Book> books;", bookLines.get(1));
    }

    @Test
    public void testGenerateOneToManyAttribute() throws SpringProjectException {
        Entity bookEntity = new Entity("Book");
        EntityGenerator bookGenerator = new EntityGenerator(bookEntity);
        Entity author = new Entity("Author");
        Attribute book = new Attribute("book", "Book");
        book.setRelationship(Relationship.ONE_TO_MANY);
        author.addAttribute(book);

        EntityGenerator generator = new EntityGenerator(author);
        Project.getInstance().addGenerator(bookGenerator);
        Project.getInstance().addGenerator(generator);
        generator.generateLines();
        HashMap<String, List<String>> lines = generator.getAttributes();
        List<String> bookLines = lines.get("book");

        Assertions.assertEquals("\t@OneToMany(mappedBy = \"author\")", bookLines.getFirst());
        Assertions.assertEquals("\tprivate List<Book> books;", bookLines.get(1));
    }

    @Test
    public void testGetAttributeImport_EntityArchitecture() throws SpringProjectException {
        ProjectPath.getInstance().setEntityArchitecture(true);

        // entity creation
        Entity author = new Entity("Author");
        Attribute bookAttribute = new Attribute("book", "Book");
        bookAttribute.setRelationship(Relationship.ONE_TO_MANY);
        author.addAttribute(bookAttribute);
        Entity book = new Entity("Book");

        // generators creation
        EntityGenerator authorGenerator = new EntityGenerator(author);
        EntityGenerator bookGenerator = new EntityGenerator(book);

        // generators added to project
        Project project = Project.getInstance();
        project.addGenerator(authorGenerator);
        project.addGenerator(bookGenerator);

        authorGenerator.generateLines();
        System.out.println(authorGenerator.getImports());

        boolean isIn = authorGenerator.getImports().contains("org.cliforspringjpa.domain.book.Book");

        Assertions.assertTrue(isIn);
   }

    @Test
    public void testGetAttributeImport_ControllerArchitecture() throws SpringProjectException {
        ProjectPath.getInstance().setEntityArchitecture(false);

        // entity creation
        Entity author = new Entity("Author");
        Attribute bookAttribute = new Attribute("book", "Book");
        bookAttribute.setRelationship(Relationship.ONE_TO_MANY);
        author.addAttribute(bookAttribute);
        Entity book = new Entity("Book");

        // generators creation
        EntityGenerator authorGenerator = new EntityGenerator(author);
        EntityGenerator bookGenerator = new EntityGenerator(book);

        // generators added to project
        Project project = Project.getInstance();
        project.addGenerator(authorGenerator);
        project.addGenerator(bookGenerator);

        authorGenerator.generateLines();

        boolean isIn = authorGenerator.getImports().contains("import org.cliforspringjpa.entity.Book;");
        boolean isListIn = authorGenerator.getImports().contains("import java.util.List;");
        System.out.println(authorGenerator.getImports());
        Assertions.assertTrue(isIn);
        Assertions.assertTrue(isListIn);
    }


}
