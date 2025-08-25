package com.homework1.MySpringbootLab.service;

import com.homework1.MySpringbootLab.controller.dto.BookDto;
import com.homework1.MySpringbootLab.entity.Book;
import com.homework1.MySpringbootLab.exception.BusinessException;
import com.homework1.MySpringbootLab.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDto.Response> getAllBooks(){
        return bookRepository.findAll().stream().map(BookDto.Response::fromEntity).toList();
    }

    public BookDto.Response getBookById(Long id){
        return bookRepository.findById(id).map(BookDto.Response::fromEntity).orElseThrow(() ->
                new BusinessException("not found book", HttpStatus.NOT_FOUND)
        );
    }

    public BookDto.Response getBookByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn).map(BookDto.Response::fromEntity)
                .orElseThrow(() ->
                        new BusinessException("not found book", HttpStatus.NOT_FOUND)
                );
    }

    public List<BookDto.Response> getBooksByAuthor(@PathVariable String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream().map(BookDto.Response::fromEntity).toList();
    }

    @Transactional
    public BookDto.Response create(BookDto.Request req){
        Book _book = Book.builder()
                .title(req.getTitle())
                .author(req.getAuthor())
                .isbn(req.getIsbn())
                .publishDate(req.getPublishDate())
                .price(req.getPrice())
                .build();

        Book savedBook = bookRepository.save(_book);
        return BookDto.Response.fromEntity(savedBook);
    }

    @Transactional
    public BookDto.Response update(Long id, BookDto.Request req){
        return bookRepository.findById(id)
                .map(existing -> {
                    if(req.getTitle() != null){
                        existing.setTitle(req.getTitle());
                    }

                    if(req.getAuthor() != null){
                        existing.setAuthor(req.getAuthor());
                    }

                    if(req.getIsbn() != null){
                        existing.setIsbn(req.getIsbn());
                    }

                    if(req.getPrice() != null){
                        existing.setPrice(req.getPrice());
                    }

                    if(req.getPublishDate() != null){
                        existing.setPublishDate(req.getPublishDate());
                    }

                    if(req.getPublishDate() != null){
                        existing.setPublishDate(req.getPublishDate());
                    }

                    Book saved = bookRepository.save(existing);

                    return BookDto.Response.fromEntity(saved);
                })
                .orElseThrow(() -> new BusinessException("not found book", HttpStatus.NOT_FOUND)
                );
    }

    @Transactional
    public void deleteBook(Long id){
        bookRepository.findById(id)
                .map(b -> {
                    bookRepository.deleteById(id);
                    return id;
                }).orElseThrow(() -> new BusinessException("not found book", HttpStatus.NOT_FOUND)
                );

    }
}
