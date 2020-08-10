package com.zy.library.controller;

import com.zy.library.entity.Book;
import com.zy.library.entity.BookSort;
import com.zy.library.service.BookService;
import com.zy.library.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Api(tags = "图书管理相关接口")
@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation("根据不同信息查询图书的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookName", value = "书名"),
            @ApiImplicitParam(name = "bookAuthor", value = "作者"),
            @ApiImplicitParam(name = "bookPress", value = "出版社"),
            @ApiImplicitParam(name = "bookSort", value = "书所属类别"),
            @ApiImplicitParam(name = "bookNameLike", value = "片段书名"),
    })
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

    @ApiOperation("根据bookId查询图书是否可借状态的接口")
    @ApiImplicitParam(name = "bookId", value = "书id", required = true)
    @GetMapping("/book/borrow_status")
    public Boolean getBorrowStatus(@RequestParam Long bookId) {
        return bookService.getBorrowStatus(bookId);
    }

    @ApiOperation("查询热门图书的接口")
    @GetMapping("/book/hot")
    public Set<Object> getHotBooks() {
        return redisUtil.reverseRange("hotBooks", 0, 2);
    }

    @ApiOperation("增加图书的接口")
    @PostMapping("/book")
    public Book saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @ApiOperation("修改图书信息的接口")
    @PutMapping("/book")
    public Book updateBook(@RequestBody Book book) {
        return bookService.updateBook(book);
    }

    @ApiOperation("根据bookId删除图书的接口")
    @ApiImplicitParam(name = "bookId", value = "书id", required = true)
    @DeleteMapping("/book")
    public String deleteBook(@RequestParam Long bookId) {
        bookService.deleteBookByBookId(bookId);
        return "success";
    }

}
