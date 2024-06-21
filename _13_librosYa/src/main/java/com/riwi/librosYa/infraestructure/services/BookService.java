package com.riwi.librosYa.infraestructure.services;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.librosYa.api.dto.DTOBook;
import com.riwi.librosYa.api.mappers.BookMapper;
import com.riwi.librosYa.domain.entities.Book;
import com.riwi.librosYa.domain.repository.BookRepository;
import com.riwi.librosYa.infraestructure.abstract_Services.IBookService;
import com.riwi.librosYa.util.enums.SortType;
import com.riwi.librosYa.util.exceptions.BadRequestException;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class BookService implements IBookService
{
    @Autowired
    private BookRepository bookRepository;
    
    private BookMapper getMapper() {
        return Mappers.getMapper(BookMapper.class);
    }
    
    @Override
    public DTOBook create(DTOBook request) {
        Book bookEntity = getMapper().toEntity(request);
        return getMapper().toDTOBook(bookRepository.save(bookEntity));
    }

    @Override
    public DTOBook get(Long id) {
        Book bookEntity = this.find(id);

        return getMapper().toDTOBook(bookEntity);
    }

    @Override
    public Page<DTOBook> searchBook(String title, String genre, String author, Pageable pageable) 
    {
        return bookRepository.findByTitleContainingAndAuthorContainingAndGenreContaining(title, author, genre, pageable).map(getMapper()::toDTOBook);
    }

    @Override
    public DTOBook update(DTOBook request, Long id) {
        Book existingEntity = this.find(id);

        Book updatedEntity = getMapper().toEntity(request);
        updatedEntity.setId(existingEntity.getId());
        
        return getMapper().toDTOBook(bookRepository.save(updatedEntity));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
    
    private Book find(Long id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No Book found with the supplied ID"));
    }

    @Override
    public Page<DTOBook> getAll(int page, int size, SortType sort) {
        // TODO Auto-generated method stub
        return null;
    }
}