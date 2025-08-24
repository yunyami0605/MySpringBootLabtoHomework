package com.homework1.MySpringbootLab.service;

import com.homework1.MySpringbootLab.controller.dto.BookDto;
import com.homework1.MySpringbootLab.entity.Book;
import com.homework1.MySpringbootLab.repository.BookRepository;
import com.homework1.MySpringbootLab.support.BusinessException;
import com.homework1.MySpringbootLab.support.ErrorObject;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDto.BookResponse> getAllBooks(){
        return bookRepository.findAll().stream().map(BookDto.BookResponse::from).toList();
    }

    public BookDto.BookResponse getBookById(Long id){
        return bookRepository.findById(id).map(BookDto.BookResponse::from).orElseThrow(() ->
                new BusinessException(
                        ErrorObject.builder()
                                .message("not found book")
                                .httpStatus(HttpStatus.valueOf(HttpStatus.NOT_FOUND.value()))
                                .build()
                )
        );
    }

    public BookDto.BookResponse getBookByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn).map(BookDto.BookResponse::from)
                .orElseThrow(() ->
                        new BusinessException(
                                ErrorObject.builder()
                                        .message("not found book")
                                        .httpStatus(HttpStatus.valueOf(HttpStatus.NOT_FOUND.value()))
                                        .build()
                        )
                );
    }

    public List<BookDto.BookResponse> getBooksByAuthor(@PathVariable String author) {
        return bookRepository.findByAuthor(author).stream().map(BookDto.BookResponse::from).toList();
    }

    public BookDto.BookResponse create(BookDto.BookCreateRequest req){
        Book _book = req.toEntity();
        Book savedBook = bookRepository.save(_book);
        return BookDto.BookResponse.from(savedBook);
    }

    public BookDto.BookResponse update(Long id, BookDto.BookUpdateRequest req){
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

                    return BookDto.BookResponse.from(saved);
                })
                .orElseThrow(() ->
                        new BusinessException(
                                ErrorObject.builder()
                                        .message("not found book")
                                        .httpStatus(HttpStatus.valueOf(HttpStatus.NOT_FOUND.value()))
                                        .build()
                        )
                );
    }

    public void deleteBook(Long id){
        bookRepository.findById(id)
                .map(b -> {
                    bookRepository.deleteById(id);
                    return id;
                }).orElseThrow(() ->
                        new BusinessException(
                                ErrorObject.builder()
                                        .message("not found book")
                                        .httpStatus(HttpStatus.valueOf(HttpStatus.NOT_FOUND.value()))
                                        .build()
                        )
                );

    }
}
