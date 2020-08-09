package com.zy.library.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "library_book_sort")
public class BookSort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sort_id")
    private Integer bookSortId;
    @Column(name = "sort_name", length = 10)
    private String bookSortName;

    public BookSort() {
    }

    public BookSort(Integer bookSortId) {
        this.bookSortId = bookSortId;
    }

    @Override
    public String toString() {
        return "BookSort{" +
                "bookSortId=" + bookSortId +
                ", bookSortName='" + bookSortName + '\'' +
                '}';
    }

    public Integer getBookSortId() {
        return bookSortId;
    }

    public void setBookSortId(Integer bookSortId) {
        this.bookSortId = bookSortId;
    }

    public String getBookSortName() {
        return bookSortName;
    }

    public void setBookSortName(String bookSortName) {
        this.bookSortName = bookSortName;
    }

}
