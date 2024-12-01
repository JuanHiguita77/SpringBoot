package com.riwi.librosYa.infraestructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.librosYa.api.dto.DTOBook;
import com.riwi.librosYa.api.mappers.BookMapper;
import com.riwi.librosYa.domain.entities.Book;
import com.riwi.librosYa.domain.repository.BookRepository;
import com.riwi.librosYa.infraestructure.abstract_Services.IBookService;
import com.riwi.librosYa.util.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookService implements IBookService {

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final BookMapper bookMapper;

    @Override
    public DTOBook create(DTOBook request) {
        Book bookEntity = bookMapper.toEntity(request);
        return bookMapper.toDTOBook(bookRepository.save(bookEntity));
    }

    @Override
    public DTOBook get(Long id) {
        Book bookEntity = find(id);
        return bookMapper.toDTOBook(bookEntity);
    }

    @Override
    public Page<DTOBook> searchBook(String title, String genre, String author, Pageable pageable) {
        return bookRepository
                .findByTitleContainingAndAuthorContainingAndGenreContaining(title, author, genre, pageable)
                .map(bookMapper::toDTOBook);
    }

    @Override
    public DTOBook update(DTOBook request, Long id) {
        Book existingEntity = find(id);
        Book updatedEntity = bookMapper.toEntity(request);
        updatedEntity.setId(existingEntity.getId());
        return bookMapper.toDTOBook(bookRepository.save(updatedEntity));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    private Book find(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No Book found with the supplied ID"));
    }
}
