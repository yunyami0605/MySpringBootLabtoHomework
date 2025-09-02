package com.homework1.MySpringbootLab.repository;

import com.homework1.MySpringbootLab.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    // 출판사 이름으로 특정 출판사를 조회합니다.
    Optional<Publisher> findByName(String name);

    // ID로 출판사를 조회하면서 해당 출판사의 모든 도서를 즉시 로딩합니다. (Fetch Join)
    @Query("SELECT p FROM Publisher p LEFT JOIN FETCH p.books WHERE p.id = :id")
    Optional<Publisher> findByIdWithBooks(@Param("id") Long id);

    // 특정 이름의 출판사가 존재하는지 확인합니다.
    boolean existsByName(String name);
}
