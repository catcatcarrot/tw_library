package com.zy.library.repository;

import com.zy.library.entity.BookUseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BookUseRecordRepository extends JpaRepository<BookUseRecord, Long> {

    @Query(value = "SELECT record_id, book_name, book_author, book_press, book_number " +
            "FROM library_book_use_record record INNER JOIN library_book book " +
            "ON record.book_id = book.book_id and record.book_actual_return_date IS NULL " +
            "AND record.user_id = ?1", nativeQuery = true)
    List<Map<String, Object>> findUserBorrowBooksByUserId(Long userId);

    @Query(value = "SELECT SUM(book_use_fee) FROM library_book_use_record " +
            "WHERE book_actual_return_date BETWEEN ?1 AND ?2", nativeQuery = true)
    BigDecimal benefitDuringDate(LocalDateTime fromDate, LocalDateTime toDate);

}
