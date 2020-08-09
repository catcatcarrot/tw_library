package com.zy.library.controller;

import com.zy.library.entity.Book;
import com.zy.library.entity.BookSort;
import com.zy.library.service.BookService;
import com.zy.library.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/books")
    public List<Book> listBooksByBookName(@RequestParam(required = false) String bookName
            , @RequestParam(required = false) String bookAuthor, @RequestParam(required = false) String bookPress
            , @RequestParam(required = false) Integer bookSort, @RequestParam(required = false) String bookNameLike) {

        List<Book> listBooks = new ArrayList<>(0);

        if (bookName != null) {
            listBooks = bookService.listBooksByBookName(bookName);

            // 热门搜索的书籍
            if (!listBooks.isEmpty()) {
                redisUtil.incrementScore("hotBooks", bookName, 1);
            }

            return listBooks;
        }

        if (bookAuthor != null) {
            listBooks = bookService.listBooksByBookAuthor(bookAuthor);
            return listBooks;
        }

        if (bookPress != null) {
            listBooks = bookService.listBooksByBookPress(bookPress);
            return listBooks;
        }

        if (bookSort != null) {
            listBooks = bookService.listBooksByBookSort(new BookSort(bookSort));
            return listBooks;
        }

        if (bookNameLike != null) {
            listBooks = bookService.listBooksByBookNameLike("%" + bookNameLike + "%");
            return listBooks;
        } else {
            listBooks = bookService.listAllBooks();
            return listBooks;
        }
    }

    @GetMapping("/book/borrow_status")
    public Boolean getBorrowStatus(@RequestParam Long bookId) {
        return bookService.getBorrowStatus(bookId);
    }

    @GetMapping("/book/hot")
    public Set<Object> getHotBooks() {
        return redisUtil.reverseRange("hotBooks", 0, 2);
    }

    @PostMapping("/book")
    public Book saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @PutMapping("/book")
    public Book updateBook(@RequestBody Book book) {
        return bookService.updateBook(book);
    }

    @DeleteMapping("/book")
    public String deleteBook(@RequestParam Long bookId) {
        bookService.deleteBookByBookId(bookId);
        return "success";
    }

}
