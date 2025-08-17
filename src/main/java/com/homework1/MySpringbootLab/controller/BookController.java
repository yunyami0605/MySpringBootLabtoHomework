package com.homework1.MySpringbootLab.controller;

import com.homework1.MySpringbootLab.entity.Book;
import com.homework1.MySpringbootLab.repository.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.homework1.MySpringbootLab.support.BusinessException;
import com.homework1.MySpringbootLab.support.ErrorObject;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;

    @GetMapping
    List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Book req) {
        bookRepository.save(req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    ResponseEntity<Book> getBookById(@PathVariable Long id){
        return bookRepository.findById(id).map(ResponseEntity::ok).orElseThrow(() ->
                new BusinessException(
                        ErrorObject.builder()
                                .message("not found book")
                                .httpStatus(HttpStatus.valueOf(HttpStatus.NOT_FOUND.value()))
                                .build()
                )
        );
    }

    @GetMapping("/isbn/{isbn}")
    public Book getUserByIsbn(@PathVariable String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() ->
                        new BusinessException(
                                ErrorObject.builder()
                                        .message("not found book")
                                        .httpStatus(HttpStatus.valueOf(HttpStatus.NOT_FOUND.value()))
                                        .build()
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id,
                                       @Valid @RequestBody Book req) {
        return bookRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(req.getTitle());
                    existing.setAuthor(req.getAuthor());
                    existing.setIsbn(req.getIsbn());
                    existing.setPrice(req.getPrice());
                    existing.setPublishDate(req.getPublishDate());
                    Book saved = bookRepository.save(existing);

                    return ResponseEntity.ok(saved);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(b -> {
                    bookRepository.deleteById(id);
                    return ResponseEntity.ok().build();
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
