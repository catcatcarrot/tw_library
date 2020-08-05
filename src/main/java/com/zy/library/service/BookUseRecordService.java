package com.zy.library.service;

import com.zy.library.entity.BookUseRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BookUseRecordService {

    List<Map<String,Object>> listUserBorrowBooks(Long userId);

    BookUseRecord saveBookUseRecord(Long userId, Long bookId);

    BookUseRecord updateBookUseRecord(Long recordId);

    BigDecimal sumBookUseIncomeDuringDate(LocalDateTime fromDate, LocalDateTime toDate);
}
