package com.zy.library.service.impl;

import com.zy.library.entity.BookUseRecord;
import com.zy.library.repository.BookUseRecordRepository;
import com.zy.library.service.BookUseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class BookUseRecordServiceImpl implements BookUseRecordService {

    @Autowired
    private BookUseRecordRepository bookUseRecordRepository;

    @Override
    public List<Map<String, Object>> listUserBorrowBooks(Long userId) {
        return bookUseRecordRepository.findUserBorrowBooksByUserId(userId);
    }

    /**
     * 借书
     */
    @Override
    public BookUseRecord saveBookUseRecord(Long userId, Long bookId) {
        // 定义借书时间与应还书时间，距离30天
        LocalDateTime userBorrowBookDate = LocalDateTime.now();
        LocalDateTime userShouldReturnBookDate = userBorrowBookDate.plusDays(30);

        BookUseRecord bookUseRecord = new BookUseRecord(userId, bookId
                , userBorrowBookDate, userShouldReturnBookDate, new BigDecimal(0));
        return bookUseRecordRepository.save(bookUseRecord);
    }

    /**
     * 还书
     */
    @Override
    public BookUseRecord updateBookUseRecord(Long recordId) {
        BookUseRecord userBorrowBookRecord = bookUseRecordRepository.getOne(recordId);

        LocalDateTime bookShouldReturnDate = userBorrowBookRecord.getBookShouldReturnDate();
        LocalDateTime bookActualReturnDate = LocalDateTime.now();

        userBorrowBookRecord.setBookActualReturnDate(bookActualReturnDate);

        BigDecimal bookUseFee = userPayFeeOverReturnDate(bookShouldReturnDate, bookActualReturnDate);
        userBorrowBookRecord.setBookUseFee(bookUseFee);
        return bookUseRecordRepository.save(userBorrowBookRecord);
    }

    public static BigDecimal userPayFeeOverReturnDate(LocalDateTime bookShouldReturnDate, LocalDateTime bookActualReturnDate) {
        Duration bookDuration = Duration.between(bookShouldReturnDate, bookActualReturnDate);

        long bookDurationDays = bookDuration.toDays();
        bookDurationDays = bookDurationDays > 0 ? bookDurationDays : 0;

        return new BigDecimal(bookDurationDays * 0.3);
    }

    @Override
    public BigDecimal benefitDuringDate(LocalDateTime fromDate, LocalDateTime toDate) {
        return bookUseRecordRepository.benefitDuringDate(fromDate, toDate);
    }


}
