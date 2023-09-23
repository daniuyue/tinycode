package org.tinycode.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinycode.mappers.BookMapper;
import org.tinycode.model.Book;

import java.util.List;


/**
 * 整合mybatis 实现增删改查1
 */
@Slf4j
@RestController
public class BookController {
    @Autowired
    BookMapper bookMapper;

    /**
     * 查询
     */
    @GetMapping("internal/daniuyue/mybatis/book")
    public ResponseEntity getBook() {
        List<Book> allBooks = bookMapper.getAllBooks();
        for (Book book : allBooks) {
            log.info(book.toString());
        }
        return new ResponseEntity(allBooks, HttpStatus.OK);
    }

    /**
     * 添加
     */
    @PostMapping("internal/daniuyue/mybatis/book")
    public ResponseEntity insertBook(@RequestBody Book book) {
        log.info(JSONObject.toJSONString(book));
        bookMapper.addBook(book);
        log.info(book.toString());
        return new ResponseEntity(book.toString(), HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("internal/daniuyue/mybatis/book")
    public ResponseEntity updateBook(@RequestBody Book updateInfo) {
        log.info("PUT internal/daniuyue/mybatis/book, book{}", JSONObject.toJSONString(updateInfo));
        int i = bookMapper.updateBookById(updateInfo);
        log.info("成功修改了" + i + "条信息");
        return new ResponseEntity(updateInfo, HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("internal/daniuyue/mybatis/book/{id}")
    public ResponseEntity delBook(@PathVariable Integer id) {
        log.info("DELETE internal/daniuyue/mybatis/book/{}", id);
        int i = bookMapper.deleteBookById(id);
        return new ResponseEntity(i, HttpStatus.OK);
    }

}
