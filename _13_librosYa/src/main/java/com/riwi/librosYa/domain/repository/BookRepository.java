package com.riwi.librosYa.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.librosYa.domain.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    Page<Book> findByTitleContainingAndAuthorContainingAndGenreContaining(String title, String author, String genre, Pageable pageable);
}
