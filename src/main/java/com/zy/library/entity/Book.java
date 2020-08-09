package com.zy.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "library_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "book_name", length = 20)
    private String bookName;
    @Column(name = "book_author", length = 20)
    private String bookAuthor;
    @Column(name = "book_press", length = 20)
    private String bookPress;
    @Column(name = "book_number", length = 20)
    private String bookNumber;

    @ManyToOne(targetEntity = BookSort.class)
    @JoinColumn(name = "book_sort", referencedColumnName = "sort_id")
    private BookSort bookSort;

    public Book() {
    }

    public Book(Long bookId, String bookName, String bookAuthor, String bookPress, String bookNumber) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPress = bookPress;
        this.bookNumber = bookNumber;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPress='" + bookPress + '\'' +
                ", bookNumber='" + bookNumber + '\'' +
                '}';
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPress() {
        return bookPress;
    }

    public void setBookPress(String bookPress) {
        this.bookPress = bookPress;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    public BookSort getBookSort() {
        return bookSort;
    }

    public void setBookSort(BookSort bookSort) {
        this.bookSort = bookSort;
    }
}
