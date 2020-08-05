package com.zy.library.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="library_book_use_record")
public class BookUseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="record_id")
    private Long recordId;
    @Column(name="book_id")
    private Long bookId;
    @Column(name="user_id")
    private Long userId;
    @Column(name="book_borrow_date")
    private LocalDateTime bookBorrowDate;
    @Column(name="book_should_return_date")
    private LocalDateTime bookShouldReturnDate;
    @Column(name="book_actual_return_date")
    private LocalDateTime bookActualReturnDate;
    @Column(name="book_use_fee",precision = 10,scale = 2)
    private BigDecimal bookUseFee;

    public BookUseRecord() {
    }

    public BookUseRecord(Long userId, Long bookId, LocalDateTime bookBorrowDate, LocalDateTime bookShouldReturnDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.bookBorrowDate = bookBorrowDate;
        this.bookShouldReturnDate = bookShouldReturnDate;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getBookBorrowDate() {
        return bookBorrowDate;
    }

    public void setBookBorrowDate(LocalDateTime bookBorrowDate) {
        this.bookBorrowDate = bookBorrowDate;
    }

    public LocalDateTime getBookShouldReturnDate() {
        return bookShouldReturnDate;
    }

    public void setBookShouldReturnDate(LocalDateTime bookShouldReturnDate) {
        this.bookShouldReturnDate = bookShouldReturnDate;
    }

    public LocalDateTime getBookActualReturnDate() {
        return bookActualReturnDate;
    }

    public void setBookActualReturnDate(LocalDateTime bookActualReturnDate) {
        this.bookActualReturnDate = bookActualReturnDate;
    }

    public BigDecimal getBookUseFee() {
        return bookUseFee;
    }

    public void setBookUseFee(BigDecimal bookUseFee) {
        this.bookUseFee = bookUseFee;
    }
}
