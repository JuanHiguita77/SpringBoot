package com.riwi.librosYa.api.controllers;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
@Tag(name = "Books")
public class BookController {

    @Autowired
    private final IBookService bookService;

    @Operation(summary = "Create a new book", description = "Send information to create a new book")
    @PostMapping
    public ResponseEntity<DTOBook> save(@Validated @RequestBody DTOBook book) {
        return ResponseEntity.ok(this.bookService.create(book));
    }

    @Operation(summary = "Delete a book by book_id", description = "Send the book book_id to delete it")
    @DeleteMapping(path = "/{book_id}")
    public ResponseEntity<Void> delete(@PathVariable Long book_id){
        this.bookService.delete(book_id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a book", description = "Send information to update a book")
    @PutMapping(path = "/{book_id}")
    public ResponseEntity<DTOBook> update(@PathVariable Long book_id, @Validated @RequestBody DTOBook book) {
        return ResponseEntity.ok(this.bookService.update(book, book_id));
    }

    @Operation(summary = "Get all books", description = "Get all books with pagination")
    @GetMapping
    public ResponseEntity<Page<DTOBook>> getAllBooks(
            @RequestParam(value = "title", defaultValue = "", required = false) String title,
            @RequestParam(value = "author", defaultValue = "", required = false) String author,
            @RequestParam(value = "genre", defaultValue = "", required = false) String genre,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size)
        {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(this.bookService.searchBook(title, author, genre, pageable));
    }

    @Operation(summary = "Get a book by book_id", description = "Send the book book_id to get book details")
    @GetMapping("/{book_id}")
    public ResponseEntity<DTOBook> findById(@PathVariable Long book_id) {
        return ResponseEntity.ok(this.bookService.get(book_id));
    }
}
