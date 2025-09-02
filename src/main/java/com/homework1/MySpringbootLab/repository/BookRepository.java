package com.homework1.MySpringbootLab.repository;

import com.homework1.MySpringbootLab.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT b FROM Book b JOIN FETCH b.bookDetail WHERE b.id = :id")
    Optional<Book> findByIdWithBookDetail(@Param("id") Long id);

    // ID로 도서를 조회하면서 BookDetail과 Publisher를 모두 즉시 로딩합니다.
//    Optional<Book> findByIdWithAllDetails(@Param("id") Long id);

    // 특정 출판사의 모든 도서를 조회합니다.
    @Query("SELECT b FROM Book b " +
            "LEFT JOIN FETCH b.bookDetail " +
            "LEFT JOIN FETCH b.publisher " +
            "WHERE b.id = :id")
    List<Book> findByPublisherId(@Param("id") Long publisherId);

    // 특정 출판사의 도서 수를 계산합니다.
    Long countByPublisherId(@Param("publisherId") Long publisherId);

    @Query("SELECT b FROM Book b JOIN FETCH b.bookDetail WHERE b.isbn = :isbn")
    Optional<Book> findByIsbnWithBookDetail(@Param("isbn") String isbn);
    Boolean existsByIsbn(String isbn);
}
