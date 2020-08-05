package com.zy.library.controller;

import com.zy.library.entity.BookUseRecord;
import com.zy.library.service.BookUseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class BookUseRecordController {

    @Autowired
    private BookUseRecordService bookUseRecordService;

    /**用户目前借的未归还的图书*/
    @GetMapping("/user_use_books")
    public List<Map<String,Object>> listUserBorrowBooks(Long userId){
        return bookUseRecordService.listUserBorrowBooks(userId);
    }

    /**在期间内图书馆收入总和*/
    @GetMapping("/sum_book_use_income")
    public BigDecimal sumBookUseIncomeDuringDate(LocalDateTime fromDate, LocalDateTime toDate){
        return bookUseRecordService.sumBookUseIncomeDuringDate(fromDate, toDate);
    }

    /**借书*/
    @PostMapping("/user_use_books")
    public BookUseRecord saveBookUseRecord(Long userId, Long bookId){
        return bookUseRecordService.saveBookUseRecord(userId, bookId);
    }

    /**还书*/
    @PutMapping("/user_use_books")
    public BookUseRecord updateBookUseRecord(Long recordId){
        return bookUseRecordService.updateBookUseRecord(recordId);
    }

}
