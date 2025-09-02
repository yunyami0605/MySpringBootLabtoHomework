package com.homework1.MySpringbootLab.controller;

import com.homework1.MySpringbootLab.controller.dto.BookDto;
import com.homework1.MySpringbootLab.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookDetailController {
    private final BookService bookService;

    // 2.1 모든 도서 조회
    @GetMapping
    ResponseEntity<List<BookDto.BookListResponse>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // 2.2 ID로 도서 조회
    @GetMapping("/{id}")
    ResponseEntity<BookDto.Response> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // 2.3 ISBN으로 도서 조회
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDto.Response> getBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(this.bookService.getBookByIsbn(isbn));
    }

    // 2.4 작가로 도서 검색
    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookDto.Response>> getBooksByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(this.bookService.getBooksByAuthor(author));
    }

    // 2.5 제목으로 도서 검색
    @GetMapping("/search/title")
    public ResponseEntity<List<BookDto.Response>> getBookByTitle(@RequestParam String title){
        return ResponseEntity.ok(bookService.getBookByTitle(title));
    }

    // 2.6 새 도서 생성 (상세 정보 포함)
    @PostMapping
    public ResponseEntity<BookDto.Response> create(@Valid @RequestBody BookDto.Request req) {
        return ResponseEntity.ok(this.bookService.create(req));
    }

    // 2.8 도서 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<BookDto.Response> update(@PathVariable Long id,
                                       @Valid @RequestBody BookDto.Request req) {
        return ResponseEntity.ok(this.bookService.update(id, req));
    }

    // 2.9 도서 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookDto.Response> patch(@PathVariable Long id, @RequestBody BookDto.PatchRequest req){
        return ResponseEntity.ok((this.bookService.patch(id, req)));
    }

    @PatchMapping("/{id}/detail")
    public ResponseEntity<BookDto.Response> patch(@PathVariable Long id, @RequestBody BookDto.BookDetailPatchRequest req){
        return ResponseEntity.ok((this.bookService.patchDetail(id, req)));
    }

}
