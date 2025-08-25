package com.homework1.MySpringbootLab.repository;

import com.homework1.MySpringbootLab.entity.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {
    Optional<BookDetail> findByBookId(Long bookId);

    // BookDetail 엔티티를 조회하고 book 필드도 같이 가져온다.
    @Query("SELECT bd FROM BookDetail bd JOIN FETCH bd.book WHERE bd.id = :id")
    Optional<BookDetail> findByIdWithBook(Long id);
    List<BookDetail> findByPublisher(String publisher);
}
