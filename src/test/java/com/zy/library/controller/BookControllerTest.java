package com.zy.library.controller;

import com.zy.library.entity.Book;
import com.zy.library.entity.BookSort;
import com.zy.library.service.BookService;
import com.zy.library.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private RedisUtil redisUtil;

    @Test
    void listBooksByBookName() throws Exception {
        List<Book> expectedOutput = new ArrayList<>(1);
        expectedOutput.add(new Book(1L, "The Little Prince", "Wikipedia", "Xi'an press", "123"));

        Mockito.when(bookService.listBooksByBookName("The Little Prince")).thenReturn(expectedOutput);

        mvc.perform(MockMvcRequestBuilders.get("/books?bookName=The Little Prince")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(expectedOutput.get(0).getBookId()))
                .andExpect(jsonPath("$[0].bookName").value(expectedOutput.get(0).getBookName()));

        Mockito.verify(redisUtil, Mockito.times(1)).incrementScore(Mockito.anyString(), Mockito.any(), Mockito.anyDouble());
    }

    @Test
    void getBorrowStatus() throws Exception {
        Boolean expectedOutput = true;

        Mockito.when(bookService.getBorrowStatus(Mockito.any())).thenReturn(expectedOutput);

        mvc.perform(MockMvcRequestBuilders.get("/book/borrow_status?bookId=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedOutput));
    }

    @Test
    void getHotBooks() throws Exception {
        Set<Object> expectedOutput = new HashSet<>(1);
        expectedOutput.add(new Book(1L, "The Little Prince", "Wikipedia", "Xi'an press", "123"));

        Mockito.when(redisUtil.reverseRange(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(expectedOutput);

        mvc.perform(MockMvcRequestBuilders.get("/book/hot")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedOutput.size()));

    }

    @Test
    void saveBook() throws Exception {
        Book expectedOutput = new Book(1L, "The Little Prince", "Wikipedia", "Xi'an press", "123");
        BookSort sort = new BookSort(1);
        expectedOutput.setBookSort(sort);

        Mockito.when(bookService.saveBook(Mockito.any())).thenReturn(expectedOutput);

        String requestJson = "{" +
                "        \"bookId\": 1,\n" +
                "        \"bookName\": \"放学后\",\n" +
                "        \"bookAuthor\": \"东野圭吾\",\n" +
                "        \"bookPress\": \"北京出版社\",\n" +
                "        \"bookNumber\": \"A34E3423T3\",\n" +
                "        \"bookSort\": {\n" +
                "            \"bookSortId\": 2,\n" +
                "            \"bookSortName\": \"文学\"\n" +
                "        }\n" +
                "    }";

        mvc.perform(MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(expectedOutput.getBookId()));

    }

    @Test
    void updateBook() throws Exception {
        Book expectedOutput = new Book(1L, "The Little Prince", "Wikipedia", "Xi'an press", "123");
        BookSort sort = new BookSort(1);
        expectedOutput.setBookSort(sort);

        Mockito.when(bookService.updateBook(Mockito.any())).thenReturn(expectedOutput);

        String requestJson = "{" +
                "        \"bookId\": 1,\n" +
                "        \"bookName\": \"The Little Prince\",\n" +
                "        \"bookAuthor\": \"东野圭吾\",\n" +
                "        \"bookPress\": \"北京出版社\",\n" +
                "        \"bookNumber\": \"A34E3423T3\",\n" +
                "        \"bookSort\": {\n" +
                "            \"bookSortId\": 2\n" +
                "        }\n" +
                "    }";

        mvc.perform(MockMvcRequestBuilders.put("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(expectedOutput.getBookId()));

    }

    @Test
    void deleteBook() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/book?bookId=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("success"));

        Mockito.verify(bookService, Mockito.times(1)).deleteBookByBookId(1L);
    }
}