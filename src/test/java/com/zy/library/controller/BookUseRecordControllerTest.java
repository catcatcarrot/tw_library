package com.zy.library.controller;

import com.zy.library.entity.BookUseRecord;
import com.zy.library.service.BookUseRecordService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookUseRecordController.class)
class BookUseRecordControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookUseRecordService bookUseRecordService;

    @Test
    void listUserBorrowBooks() throws Exception {
        List<Map<String,Object>> expectedOutput = new ArrayList<>();
        Map<String,Object> bookRecord = new HashMap<>();
        bookRecord.put("recordId", 1L);
        bookRecord.put("bookName", "The Little Prince");
        expectedOutput.add(bookRecord);

        Mockito.when(bookUseRecordService.listUserBorrowBooks(Mockito.any())).thenReturn(expectedOutput);

        mvc.perform(MockMvcRequestBuilders.get("/user_use_books?userId=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recordId").value(expectedOutput.get(0).get("recordId")))
                .andExpect(jsonPath("$[0].bookName").value(expectedOutput.get(0).get("bookName")));

    }

    @Test
    void sumBookUseIncomeDuringDate() throws Exception {
        BigDecimal expectedOutput = new BigDecimal(100);

        Mockito.when(bookUseRecordService.sumBookUseIncomeDuringDate(Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class))).thenReturn(expectedOutput);

        mvc.perform(MockMvcRequestBuilders.get("/sum_book_use_income?fromDate=2020-08-01 00:00:00&toDate=2020-08-06 23:59:59")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedOutput));
    }

    @Test
    void saveBookUseRecord() throws Exception {
        BookUseRecord expectedOutput = new BookUseRecord(1L, 1L, LocalDateTime.now(), LocalDateTime.now());

        Mockito.when(bookUseRecordService.saveBookUseRecord(Mockito.any(), Mockito.any())).thenReturn(expectedOutput);

        mvc.perform(MockMvcRequestBuilders.post("/user_use_books?userId=1&bookId=1"))
                //.accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(expectedOutput.getUserId()))
                .andExpect(jsonPath("$.bookId").value(expectedOutput.getBookId()));
    }

    @Test
    void updateBookUseRecord() throws Exception {
        BookUseRecord expectedOutput = new BookUseRecord();
        expectedOutput.setRecordId(1L);

        Mockito.when(bookUseRecordService.updateBookUseRecord(Mockito.any())).thenReturn(expectedOutput);

        mvc.perform(MockMvcRequestBuilders.put("/user_use_books?recordId=1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recordId").value(expectedOutput.getRecordId()));
    }
}