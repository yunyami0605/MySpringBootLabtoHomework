package com.homework1.MySpringbootLab.controller.dto;

import com.homework1.MySpringbootLab.entity.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class BookDto {

    // 도서 생성 시 사용되는 DTO
    @Getter @Setter
    public static class BookCreateRequest{

        @NotBlank(message = "제목은 필수 입력사항입니다.")
        private String title;

        @NotBlank(message = "작가명은 필수 입력사항입니다.")
        private String author;

        @NotBlank(message = "책 isbn은 필수 입력사항입니다.")
        private String isbn;

        @NotNull(message = "가격은 필수 입력사항입니다.")
        private Integer price;

        @NotNull(message = "출판일은 필수 입력사항입니다.")
        private LocalDate publishDate;

        public Book toEntity() {
            Book _book = Book.builder()
                    .title(this.title)
                    .author(this.author)
                    .isbn(this.isbn)
                    .price(this.price)
                    .publishDate(this.publishDate)
                    .build();

            return _book;
        }
    }

    // 도서 정보 업데이트 시 사용되는 DTO
    @Getter @Setter
    public static class BookUpdateRequest{
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;
    }

    // 클라이언트에게 반환되는 도서 정보 DTO
    @Data
    @Builder
    public static class BookResponse{
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        public static BookResponse from(Book _book){
            return BookResponse.builder()
                    .id(_book.getId())
                    .title(_book.getTitle())
                    .author(_book.getAuthor())
                    .isbn(_book.getIsbn())
                    .price(_book.getPrice())
                    .publishDate(_book.getPublishDate()).build();
        }
    }
}
