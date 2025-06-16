package com.flixfix.sqs_testing.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByLentFalse();

    List<BookEntity> findByLentTrue();

    List<BookEntity> findByTitleContainingIgnoreCase(String query);
}

