package com.homework1.MySpringbootLab.service;

import com.homework1.MySpringbootLab.controller.dto.BookDto;
import com.homework1.MySpringbootLab.entity.Book;
import com.homework1.MySpringbootLab.entity.BookDetail;
import com.homework1.MySpringbootLab.exception.BusinessException;
import com.homework1.MySpringbootLab.exception.ErrorCode;
import com.homework1.MySpringbootLab.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDto.Response> getAllBooks(){
        log.info("here");
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

    public List<BookDto.Response> getBookByIdWithPublisher(Long id){
        return bookRepository.findByPublisherId(id).stream().map(BookDto.Response::fromEntity).toList();
    }

    public List<BookDto.Response> getBooksByAuthor(@PathVariable String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream().map(BookDto.Response::fromEntity).toList();
    }

    public List<BookDto.Response> getBookByTitle(@PathVariable String title){
        return bookRepository.findByTitleContainingIgnoreCase(title).stream().map(BookDto.Response::fromEntity).toList();
    }

    @Transactional
    public BookDto.Response create(BookDto.Request req){
        BookDetail bookDetail = null;
        if(req.getDetailRequest() != null){
            bookDetail = BookDetail.builder()
                    .description(req.getDetailRequest().getDescription())
                    .language(req.getDetailRequest().getLanguage())
                    .pageCount(req.getDetailRequest().getPageCount())
                    .publisher(req.getDetailRequest().getPublisher())
                    .coverImageUrl(req.getDetailRequest().getCoverImageUrl())
                    .edition(req.getDetailRequest().getEdition())
                    .build();
        }

        Book _book = Book.builder()
                .title(req.getTitle())
                .author(req.getAuthor())
                .isbn(req.getIsbn())
                .publishDate(req.getPublishDate())
                .price(req.getPrice())
                .bookDetail(bookDetail)
                .build();

        if (bookDetail != null) {
            bookDetail.setBook(_book);
        }

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

    @Transactional
    public BookDto.Response patch(Long id, BookDto.PatchRequest request){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        if (request.getTitle() != null) book.setTitle(request.getTitle());
        if (request.getAuthor() != null) book.setAuthor(request.getAuthor());

        // ISBN 변경 시 중복 체크
        if (request.getIsbn() != null &&
                !request.getIsbn().equals(book.getIsbn())) {
            if (bookRepository.existsByIsbn(request.getIsbn())) {
                throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
            }
            book.setIsbn(request.getIsbn());
        }

        if (request.getPrice() != null) book.setPrice(request.getPrice());
        if (request.getPublishDate() != null) book.setPublishDate(request.getPublishDate());

        // BookDetail patch도 포함 가능
        if (request.getDetailRequest() != null) {
            BookDetail detail = book.getBookDetail();
            if (detail == null) {
                throw new EntityNotFoundException("BookDetail not found for book id " + id);
            }

            BookDto.BookDetailPatchRequest detailReq = request.getDetailRequest();
            if (detailReq.getDescription() != null) detail.setDescription(detailReq.getDescription());
            if (detailReq.getLanguage() != null) detail.setLanguage(detailReq.getLanguage());
            if (detailReq.getPageCount() != null) detail.setPageCount(detailReq.getPageCount());
            if (detailReq.getPublisher() != null) detail.setPublisher(detailReq.getPublisher());
            if (detailReq.getCoverImageUrl() != null) detail.setCoverImageUrl(detailReq.getCoverImageUrl());
            if (detailReq.getEdition() != null) detail.setEdition(detailReq.getEdition());
        }

        return BookDto.Response.fromEntity(book);
    }

    @Transactional
    public BookDto.Response patchDetail(Long id, BookDto.BookDetailPatchRequest req) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        BookDetail detail = book.getBookDetail();
        if (detail == null) {
            throw new EntityNotFoundException("BookDetail not found for book id: " + id);
        }

        if (req.getDescription() != null) detail.setDescription(req.getDescription());
        if (req.getLanguage() != null) detail.setLanguage(req.getLanguage());
        if (req.getPageCount() != null) detail.setPageCount(req.getPageCount());
        if (req.getPublisher() != null) detail.setPublisher(req.getPublisher());
        if (req.getCoverImageUrl() != null) detail.setCoverImageUrl(req.getCoverImageUrl());
        if (req.getEdition() != null) detail.setEdition(req.getEdition());

        return BookDto.Response.fromEntity(book);
    }
}
