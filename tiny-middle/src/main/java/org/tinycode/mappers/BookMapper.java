package org.tinycode.mappers;

import org.tinycode.model.Book;

import java.util.List;

public interface BookMapper {

    void addBook(Book book);

    int deleteBookById(Integer id);

    int updateBookById(Book book);

    Book getBookById(Integer id);

    List<Book> getAllBooks();

}
