package com.zy.library.service;

import com.zy.library.entity.Book;
import com.zy.library.entity.BookSort;

import java.util.List;

public interface BookService {

    List<Book> listBooksByBookName(String bookName);

    List<Book> listBooksByBookAuthor(String bookAuthor);

    List<Book> listBooksByBookPress(String bookPress);

    List<Book> listBooksByBookSort(BookSort sort);

    List<Book> listBooksByBookNameLike(String bookNameLike);

    List<Book> listAllBooks();

    Book saveBook(Book book, BookSort sort);

    Book updateBook(Book book);

    void deleteBookByBookId(Long bookId);

    Boolean canBeBorrowed(Long bookId);

}
