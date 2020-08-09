package com.zy.library.service.impl;

import com.zy.library.entity.Book;
import com.zy.library.entity.BookSort;
import com.zy.library.entity.Role;
import com.zy.library.repository.BookRepository;
import com.zy.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void listBooksByBookName() {
        String input = "放学后";

        List<Book> expectedOutput = new ArrayList<>(1);
        Book book = new Book(1L,"放学后","东野圭吾","西安出版社","AC123");
        expectedOutput.add(book);

        Mockito.when(bookRepository.findByBookName(input)).thenReturn(expectedOutput);

        assertEquals(expectedOutput, bookService.listBooksByBookName(input));
    }

    @Test
    void saveBook() {
        Book inputBook = new Book(1L,"放学后","东野圭吾","西安出版社","AC123");
        BookSort bookSort = new BookSort(1);
        inputBook.setBookSort(bookSort);

        Book expectedOutput = new Book();

        Mockito.when(bookRepository.save(inputBook)).thenReturn(expectedOutput);

        assertEquals(expectedOutput, bookService.saveBook(inputBook));
    }

    @Test
    void updateBook() {
        Book input = new Book(1L,"放学后","东野圭吾","西安出版社","AC123");
        BookSort bookSort = new BookSort(1);
        input.setBookSort(bookSort);

        Book expectedOutput = new Book(1L,"放学后啊","东野圭吾","西安出版社","AC123");

        Mockito.when(bookRepository.getOne(input.getBookId())).thenReturn(input);
        Mockito.when(bookRepository.save(input)).thenReturn(expectedOutput);

        assertEquals(expectedOutput, bookService.updateBook(input));

    }

    @Test
    void deleteBookByBookId() {
        Long input = 1L;

        bookService.deleteBookByBookId(1L);

        Mockito.verify(bookRepository, times(1)).deleteBookByBookId(input);

    }

    @Test
    void getBorrowStatus() {
        Long input = 1L;
        Boolean expectedOutput = false;

        when(bookRepository.isBorrowed(input)).thenReturn(1L);

        assertEquals(expectedOutput, bookService.getBorrowStatus(input));

    }
}