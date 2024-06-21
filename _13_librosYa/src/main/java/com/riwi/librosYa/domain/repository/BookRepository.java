package com.riwi.librosYa.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.librosYa.domain.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
    Page<Book> findByTitleContainingAndAuthorContainingAndGenreContaining(String title, String author, String genre, Pageable pageable);
}
