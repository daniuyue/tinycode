package org.tinycode.model;

import lombok.Data;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
public class Book {

    private Integer id;
    private String name;
    private String author;

    public Book() {
    }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }
}
