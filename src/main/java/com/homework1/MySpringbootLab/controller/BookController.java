package com.homework1.MySpringbootLab.controller;

import com.homework1.MySpringbootLab.controller.dto.BookDto;
import com.homework1.MySpringbootLab.entity.Book;
import com.homework1.MySpringbootLab.repository.BookRepository;
import com.homework1.MySpringbootLab.service.BookService;
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
    private final BookService bookService;

    @GetMapping
    ResponseEntity<List<BookDto.BookResponse>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    ResponseEntity<BookDto.BookResponse> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDto.BookResponse> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(this.bookService.getBookByIsbn(isbn));
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookDto.BookResponse>> getBooksByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(this.bookService.getBooksByAuthor(author));
    }

    @PostMapping
    public ResponseEntity<BookDto.BookResponse> create(@Valid @RequestBody Book req) {
        return ResponseEntity.ok(this.bookService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto.BookResponse> update(@PathVariable Long id,
                                       @Valid @RequestBody Book req) {
        return ResponseEntity.ok(this.bookService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}
