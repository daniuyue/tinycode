package org.tinycode.mappers;

import org.springframework.stereotype.Controller;
import org.tinycode.model.Book;

import java.util.List;

@Controller
public interface BookMapper {

    void addBook(Book book);

    int deleteBookById(Integer id);

    int updateBookById(Book book);

    Book getBookById(Integer id);

    List<Book> getAllBooks();

}
