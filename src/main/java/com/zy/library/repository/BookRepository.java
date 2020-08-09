package com.zy.library.repository;

import com.zy.library.entity.Book;
import com.zy.library.entity.BookSort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * javax.persistence.TransactionRequiredException: Executing an update/delete query
 */

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByBookName(String bookName);

    List<Book> findByBookAuthor(String bookAuthor);

    List<Book> findByBookPress(String bookPress);

    List<Book> findByBookSort(BookSort sort);

    List<Book> findByBookNameLike(String bookName);

    /**
     * 这本书能否被借,查到的结果如果为非空则不能借
     */
    @Query(value = "SELECT book.book_id FROM library_book book " +
            "WHERE  (book.is_deleted = 1 OR book.book_id IN " +
            "(SELECT record.book_id FROM library_book_use_record  record " +
            "WHERE record.book_actual_return_date IS NULL)) AND book.book_id=?1", nativeQuery = true)
    Long isBorrowed(Long bookId);

    /**
     * 为了保持用户借书记录，书籍采用逻辑删除
     */
    @Query(value = "UPDATE library_book SET is_deleted = 1 WHERE book_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteBookByBookId(Long bookId);

}
