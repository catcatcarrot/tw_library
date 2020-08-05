package com.zy.library.service.impl;

import com.zy.library.entity.Book;
import com.zy.library.entity.BookSort;
import com.zy.library.repository.BookRepository;
import com.zy.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> listBooksByBookName(String bookName) {
        return bookRepository.findByBookName(bookName);
    }

    @Override
    public List<Book> listBooksByBookAuthor(String bookAuthor) {
        return bookRepository.findByBookAuthor(bookAuthor);
    }

    @Override
    public List<Book> listBooksByBookPress(String bookPress) {
        return bookRepository.findByBookPress(bookPress);
    }

    @Override
    public List<Book> listBooksByBookSort(BookSort sort) {
        return bookRepository.findByBookSort(sort);
    }

    @Override
    public List<Book> listBooksByBookNameLike(String bookNameLike) {
        return bookRepository.findByBookNameLike(bookNameLike);
    }

    @Override
    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book saveBook(Book book, BookSort sort) {
        book.setBookSort(sort);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        Book bookBeforeUpdated = bookRepository.getOne(book.getBookId());

        if (book.getBookName() != null) {
            bookBeforeUpdated.setBookName(book.getBookName());
        }
        if (book.getBookAuthor() != null) {
            bookBeforeUpdated.setBookAuthor(book.getBookAuthor());
        }
        if (book.getBookPress() != null) {
            bookBeforeUpdated.setBookPress(book.getBookPress());
        }
        return bookRepository.save(bookBeforeUpdated);
    }

    @Override
    public void deleteBookByBookId(Long bookId) {
        bookRepository.deleteBookByBookId(bookId);
    }


    @Override
    public Boolean canBeBorrowed(Long bookId) {
        Long borrowBookId = bookRepository.isBorrowed(bookId);
        if (borrowBookId == null) {
            return true;
        }
        return false;
    }


}
