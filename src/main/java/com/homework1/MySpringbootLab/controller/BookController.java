package com.homework1.MySpringbootLab.controller;

import com.homework1.MySpringbootLab.controller.dto.BookDto;
import com.homework1.MySpringbootLab.repository.BookRepository;
import com.homework1.MySpringbootLab.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;
    private final BookService bookService;

    @GetMapping
    ResponseEntity<List<BookDto.Response>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    ResponseEntity<BookDto.Response> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDto.Response> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(this.bookService.getBookByIsbn(isbn));
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookDto.Response>> getBooksByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(this.bookService.getBooksByAuthor(author));
    }

    @PostMapping
    public ResponseEntity<BookDto.Response> create(@Valid @RequestBody BookDto.Request req) {
        return ResponseEntity.ok(this.bookService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto.Response> update(@PathVariable Long id,
                                       @Valid @RequestBody BookDto.Request req) {
        return ResponseEntity.ok(this.bookService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}
