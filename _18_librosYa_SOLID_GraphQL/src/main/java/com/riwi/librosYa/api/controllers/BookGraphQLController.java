package com.riwi.librosYa.api.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.riwi.librosYa.api.dto.DTOBook;
import com.riwi.librosYa.infraestructure.abstract_Services.IBookService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@Tag(name = "Books")
public class BookGraphQLController {

    private static final Logger logger = LoggerFactory.getLogger(BookGraphQLController.class);

    private final IBookService bookService;

    // Query para obtener un libro por ID
    @QueryMapping
    public DTOBook findBookById(@Argument Long book_id) {
        logger.info("Fetching book with ID: {}", book_id);
        DTOBook book = bookService.get(book_id);
        logger.info("Book details: {}", book);
        return book;
    }

    // Query para obtener todos los libros con filtros
    @QueryMapping
    public Iterable<DTOBook> getAllBooks(@Argument String title,
                                         @Argument String author,
                                         @Argument String genre,
                                         @Argument int publicationYear,
                                         @Argument String isbn,
                                         @Argument int page,
                                         @Argument int size) {
        logger.info("Fetching books with filters: title={}, author={}, genre={}, page={}, size={}",
                title, author, genre, page, size);
        Pageable pageable = PageRequest.of(page - 1, size);

        return bookService.searchBook(title, author, genre, pageable);
    }

    // Mutation para crear un nuevo libro
    @MutationMapping
    public DTOBook createBook(@Argument DTOBook book) {
        logger.info("Creating a new book: {}", book);
        DTOBook createdBook = bookService.create(book);
        logger.info("Book created successfully with ID: {}", createdBook.getId());
        return createdBook;
    }

    // Mutation para actualizar un libro existente
    @MutationMapping
    public DTOBook updateBook(@Argument Long book_id, @Argument DTOBook book) {
        logger.info("Updating book with ID: {}", book_id);
        DTOBook updatedBook = bookService.update(book, book_id);
        logger.info("Book updated successfully: {}", updatedBook);
        return updatedBook;
    }

    // Mutation para eliminar un libro
    @MutationMapping
    public Boolean deleteBook(@Argument Long book_id) {
        logger.info("Request received to delete book with ID: {}", book_id);
        bookService.delete(book_id);
        logger.info("Book deleted successfully");
        return true;
    }
}
