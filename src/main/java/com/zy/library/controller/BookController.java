package com.zy.library.controller;

import com.zy.library.entity.Book;
import com.zy.library.entity.BookSort;
import com.zy.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> listBooksByBookName(@RequestParam(required = false) String bookName
        , @RequestParam(required = false) String bookAuthor, @RequestParam(required = false) String bookPress
        , @RequestParam(required = false) Integer bookSort, @RequestParam(required = false) String bookNameLike){

        List<Book> listBooks;

        if (bookName != null) {
            listBooks = bookService.listBooksByBookName(bookName);
        } else if (bookAuthor != null) {
            listBooks = bookService.listBooksByBookAuthor(bookAuthor);
        } else if (bookPress != null) {
            listBooks = bookService.listBooksByBookPress(bookPress);
        } else if (bookSort != null) {
            listBooks = bookService.listBooksByBookSort(new BookSort(bookSort));
        } else if (bookNameLike != null) {
            listBooks = bookService.listBooksByBookNameLike("%"+bookNameLike+"%");
        } else {
            listBooks = bookService.listAllBooks();
        }

        return listBooks;
    }

    @GetMapping("/book/can_be_borrowed")
    public Boolean canBeBorrowed(Long bookId){
        return bookService.canBeBorrowed(bookId);
    }

    @PostMapping("/book")
    public Book saveBook(Book book, BookSort sort){
        return bookService.saveBook(book, sort);
    }

    @PutMapping("/book")
    public Book updateBook(Book book){
        return bookService.updateBook(book);
    }

    @DeleteMapping("/book")
    public void deleteBook(Long bookId){
        bookService.deleteBookByBookId(bookId);
    }

}
