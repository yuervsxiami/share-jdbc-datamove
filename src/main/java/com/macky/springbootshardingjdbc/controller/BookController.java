package com.macky.springbootshardingjdbc.controller;

import com.macky.springbootshardingjdbc.entity.Book;
import com.macky.springbootshardingjdbc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Macky
 * @Title class BookController
 * @Description: TODO
 * @date 2019/7/12 20:53
 */
@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public Book getItems(Long bookId){
        return bookService.getById(bookId);
    }

    @RequestMapping(value = "/book",method = RequestMethod.POST)
    public Boolean saveItem(@RequestBody  Book book){
        return bookService.save(book);
    }
}
