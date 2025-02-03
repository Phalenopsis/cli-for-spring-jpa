package org.cliforspringjpa.generator;

import org.cliforspringjpa.domain.Attribute;
import org.cliforspringjpa.domain.Entity;
import org.cliforspringjpa.domain.Relationship;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class TestEntityMethodsGenerator {
    @Test
    public void testGenerateGettersAndSetters() {
        Entity bookEntity = new Entity("Book");
        Attribute price = new Attribute("price", "double");
        bookEntity.addAttribute(price);

        EntityMethodsGenerator generator = new EntityMethodsGenerator(bookEntity);
        List<String> lines = generator.generateGettersAndSetters();

        Assertions.assertEquals("\tpublic double getPrice() {", lines.getFirst());
        Assertions.assertEquals("\t\treturn price;", lines.get(1));
        Assertions.assertEquals("\t}", lines.get(2));
        Assertions.assertEquals("", lines.get(3));
        Assertions.assertEquals("\tpublic void setPrice(double pPrice) {", lines.get(4));
        Assertions.assertEquals("\t\tprice = pPrice;", lines.get(5));
        Assertions.assertEquals("\t}", lines.get(6));
    }

    @Test
    public void testGenerateGettersAndSetters_WithManyRelationship() {
        Entity authorEntity = new Entity("Author");
        Attribute book = new Attribute("book", "Book");
        book.setRelationship(Relationship.ONE_TO_MANY);
        authorEntity.addAttribute(book);

        EntityMethodsGenerator generator = new EntityMethodsGenerator(authorEntity);
        List<String> lines = generator.generateGettersAndSetters();

        Assertions.assertEquals("\tpublic List<Book> getBooks() {", lines.getFirst());
        Assertions.assertEquals("\t\treturn books;", lines.get(1));
        Assertions.assertEquals("\t}", lines.get(2));
        Assertions.assertEquals("", lines.get(3));
        Assertions.assertEquals("\tpublic void setBooks(List<Book> bookList) {", lines.get(4));
        Assertions.assertEquals("\t\tbooks = bookList;", lines.get(5));
        Assertions.assertEquals("\t}", lines.get(6));
    }
}
