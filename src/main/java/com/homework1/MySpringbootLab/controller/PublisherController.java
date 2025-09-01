package com.homework1.MySpringbootLab.controller;

import com.homework1.MySpringbootLab.controller.dto.BookDto;
import com.homework1.MySpringbootLab.controller.dto.PublisherDTO;
import com.homework1.MySpringbootLab.service.BookService;
import com.homework1.MySpringbootLab.service.PublishersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublishersService publishersService;
    private final BookService bookService;

    // 1.1 모든 출판사 조회
    @GetMapping
    ResponseEntity<List<PublisherDTO.SimpleResponse>> getAllPublishers(){
        return ResponseEntity.ok(publishersService.getAllPublishers());
    }

    // 1.2 특정 출판사 조회
    @GetMapping("/{id}")
    ResponseEntity<PublisherDTO.Response> getPublisherById(@PathVariable Long id) {

        return ResponseEntity.ok(publishersService.getPublisherById(id));
    }

    // 1.3 출판사 이름으로 조회
    @GetMapping("/name/{name}")
    ResponseEntity<PublisherDTO.Response> getPublisherByName(@PathVariable String name){
        return ResponseEntity.ok(publishersService.getPublisherByName(name));
    }

    // 1.4 출판사별 도서 목록 조회
    @GetMapping("/{id}/books")
    ResponseEntity<List<BookDto.Response>> getBookPerPublisher(@PathVariable Long id){
//        ResponseEntity<BookDto.Response> getBookPerPublisher(@PathVariable Long id){
        return ResponseEntity.ok(this.bookService.getBookByIdWithPublisher(id));
    }


    // 1.5 새 출판사 생성
    @PostMapping()
    PublisherDTO.Response createPublisher(@Valid @RequestBody PublisherDTO.Request request){
        return publishersService.createPublisher(request);
    }

    // 1.6 출판사 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO.Response> updatePublisher(@PathVariable Long id, @Valid @RequestBody PublisherDTO.Request request) {
        return ResponseEntity.ok(publishersService.updatePublisher(id, request));
    }

    // 1.7 출판사 삭제 (도서가 없는 경우)
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePublisher(@PathVariable Long id){
        publishersService.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }
}
