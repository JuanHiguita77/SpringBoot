package com.riwi.librosYa.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.librosYa.api.dto.DTOBook;
import com.riwi.librosYa.infraestructure.abstract_Services.IBookService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
@Tag(name = "Books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private final IBookService bookService;

    @PostMapping
    public ResponseEntity<DTOBook> save(@Validated @RequestBody DTOBook book) {
        logger.info("Creating a new book: {}", book);
        DTOBook createdBook = this.bookService.create(book);
        logger.info("Book created successfully with ID: {}", createdBook.getId());
        return ResponseEntity.ok(createdBook);
    }

    @DeleteMapping(path = "/{book_id}")
    public ResponseEntity<Void> delete(@PathVariable Long book_id) {
        logger.info("Request received to delete book with ID: {}", book_id);
        this.bookService.delete(book_id);
        logger.info("Book deleted successfully");
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{book_id}")
    public ResponseEntity<DTOBook> update(@PathVariable Long book_id, @Validated @RequestBody DTOBook book) {
        logger.info("Updating book with ID: {}", book_id);
        DTOBook updatedBook = this.bookService.update(book, book_id);
        logger.info("Book updated successfully: {}", updatedBook);
        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping
    public ResponseEntity<Page<DTOBook>> getAllBooks(
            @RequestParam(value = "title", defaultValue = "", required = false) String title,
            @RequestParam(value = "author", defaultValue = "", required = false) String author,
            @RequestParam(value = "genre", defaultValue = "", required = false) String genre,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        logger.info("Fetching books with filters: title={}, author={}, genre={}, page={}, size={}",
                title, author, genre, page, size);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<DTOBook> books = this.bookService.searchBook(title, author, genre, pageable);
        logger.info("Fetched {} books", books.getTotalElements());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{book_id}")
    public ResponseEntity<DTOBook> findById(@PathVariable Long book_id) {
        logger.info("Fetching book with ID: {}", book_id);
        DTOBook book = this.bookService.get(book_id);
        logger.info("Book details: {}", book);
        return ResponseEntity.ok(book);
    }
}
