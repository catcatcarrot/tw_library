package com.zy.library.controller;

import com.zy.library.entity.BookUseRecord;
import com.zy.library.service.BookUseRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Api(tags = "书籍使用情况相关的接口")
@RestController
public class BookUseRecordController {

    @Autowired
    private BookUseRecordService bookUseRecordService;

    /**
     * 用户目前借的未归还的图书
     */
    @ApiOperation("根据userId查询用户所借图书的接口")
    @ApiImplicitParam(name = "bookId", value = "书id", required = true)
    @GetMapping("/user_borrow_books")
    public List<Map<String, Object>> listUserBorrowBooks(@RequestParam Long userId) {
        return bookUseRecordService.listUserBorrowBooks(userId);
    }

    /**
     * 在期间内图书馆收入总和
     */
    @ApiOperation("根据fromDate和toDate查询图书馆该时间内收益的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fromDate", value = "起始时间", required = true),
            @ApiImplicitParam(name = "toDate", value = "结束时间", required = true)
    })
    @GetMapping("/benefit")
    public BigDecimal benefitDuringDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate
            , @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {
        return bookUseRecordService.benefitDuringDate(fromDate, toDate);
    }

    /**
     * 借书
     */
    @ApiOperation("根据userId和bookId记录用户借书的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true),
            @ApiImplicitParam(name = "bookId", value = "图书id", required = true)
    })
    @PostMapping("/book/{userId}/borrow/{bookId}")
    public BookUseRecord saveBookUseRecord(@PathVariable Long userId, @PathVariable Long bookId) {
        return bookUseRecordService.saveBookUseRecord(userId, bookId);
    }

    /**
     * 还书
     */
    @ApiOperation("根据recordId还书的接口")
    @ApiImplicitParam(name = "recordId", value = "书籍使用记录表id", required = true)
    @PutMapping("/user_return_book")
    public BookUseRecord updateBookUseRecord(@RequestParam Long recordId) {
        return bookUseRecordService.updateBookUseRecord(recordId);
    }

}
