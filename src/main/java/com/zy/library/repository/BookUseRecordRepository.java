package com.zy.library.repository;

import com.zy.library.entity.BookUseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BookUseRecordRepository extends JpaRepository<BookUseRecord, Long> {

    @Query(value = "SELECT record_id,book.book_name,book.book_author,book.book_press,book.book_number " +
            "FROM library_book book, library_book_use_record record " +
            "WHERE record.user_id = ?1 " +
            "AND record.book_actual_return_date IS NULL " +
            "AND book.book_id = record.book_id", nativeQuery = true)
    List<Map<String, Object>> findUserBorrowBooksByUserId(Long userId);

    @Query(value = "SELECT SUM(book_use_fee) FROM library_book_use_record " +
            "WHERE book_actual_return_date BETWEEN ?1 AND ?2", nativeQuery = true)
    BigDecimal benefitDuringDate(LocalDateTime fromDate, LocalDateTime toDate);

}
