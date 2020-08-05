package com.zy.library.service.impl;

import com.zy.library.entity.BookUseRecord;
import com.zy.library.repository.BookUseRecordRepository;
import com.zy.library.service.BookUseRecordService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookUseRecordServiceImplTest {

    @Autowired
    private BookUseRecordService bookUseRecordService;

    @MockBean
    private BookUseRecordRepository bookUseRecordRepository;

    @Test
    void listUserBorrowBooks() {
        Long input = 1L;

        Map<String,Object> recordMap = new HashMap<>(4);
        recordMap.put("recordId", 1L);
        recordMap.put("bookName", "放学后");
        List<Map<String,Object>> expectedOutput = Collections.singletonList(recordMap);

        Mockito.when(bookUseRecordRepository.findUserBorrowBooksByUserId(input)).thenReturn(expectedOutput);

        assertEquals(expectedOutput, bookUseRecordService.listUserBorrowBooks(input));

    }

    @Test
    void saveBookUseRecord() {
        Long inputUserId = 1L;
        Long inputBookId = 1L;

        BookUseRecord bookUseRecord = new BookUseRecord();
        bookUseRecord.setUserId(inputUserId);
        bookUseRecord.setBookId(inputBookId);

        BookUseRecord expectedOutput = new BookUseRecord();

        Mockito.when(bookUseRecordRepository.save(Mockito.any())).thenReturn(expectedOutput);

        assertEquals(expectedOutput, bookUseRecordService.saveBookUseRecord(inputUserId, inputBookId));
    }

    @Test
    void updateBookUseRecord() {
        Long input = 1L;

        BookUseRecord userBorrowBookRecord = new BookUseRecord();
        userBorrowBookRecord.setBookId(1L);
        userBorrowBookRecord.setUserId(1L);
        userBorrowBookRecord.setBookShouldReturnDate(LocalDateTime.now());

        BookUseRecord expectedOutput = new BookUseRecord();

        Mockito.when(bookUseRecordRepository.getOne(input)).thenReturn(userBorrowBookRecord);

        Mockito.when(bookUseRecordRepository.save(Mockito.any())).thenReturn(expectedOutput);

        assertEquals(expectedOutput, bookUseRecordService.updateBookUseRecord(input));
    }

    @Test
    void userPayFeeOverReturnDateTest(){
        LocalDateTime inputBookShouldReturnDate  = LocalDateTime.of(2020,8,1,6,30,20);
        LocalDateTime inputBookActualReturnDate = LocalDateTime.of(2020,8,31,8,30,20);

        BigDecimal expectedOutput = new BigDecimal(9);

        assertEquals(expectedOutput,
                BookUseRecordServiceImpl.userPayFeeOverReturnDate(inputBookShouldReturnDate,inputBookActualReturnDate));
    }

    @Test
    void sumBookUseIncomeDuringDate() {
        LocalDateTime inputFromDate = LocalDateTime.of(2020,8,1,6,30,20);
        LocalDateTime inputToDate = LocalDateTime.of(2020,8,1,6,30,20);

        BigDecimal expectedOutput = new BigDecimal(9);

        Mockito.when(bookUseRecordRepository.sumBookUseIncomeDuringDate(inputFromDate,inputToDate))
                .thenReturn(expectedOutput);

        assertEquals(expectedOutput, bookUseRecordService.sumBookUseIncomeDuringDate(inputFromDate, inputToDate));
    }
}