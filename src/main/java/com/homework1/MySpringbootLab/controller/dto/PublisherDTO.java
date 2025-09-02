package com.homework1.MySpringbootLab.controller.dto;

import com.homework1.MySpringbootLab.entity.Book;
import com.homework1.MySpringbootLab.entity.Publisher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PublisherDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "Publisher name is required")
        @Size(max = 100, message = "Publisher name cannot exceed 100 characters")
        private String name;

        @PastOrPresent(message = "Established date cannot be in the future")
        private LocalDate establishedDate;

        @Size(max = 200, message = "Address cannot exceed 200 characters")
        private String address;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String name;
        private LocalDate establishedDate;
        private String address;
        private Long bookCount;
        private List<BookDto.ByPublisher> books;

        public static Response fromEntity(Publisher publisher) {
            List<Book> _books = publisher.getBooks();
            return Response.builder()
                    .id(publisher.getId())
                    .name(publisher.getName())
                    .establishedDate(publisher.getEstablishedDate())
                    .address(publisher.getAddress())
                    .bookCount(_books != null ?(long) _books.size() : 0)
                    .books(_books != null ? _books.stream()
                            .map(BookDto.ByPublisher::fromEntity)
                            .collect(Collectors.toList()) : List.of())
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SimpleResponse {
        private Long id;
        private String name;
        private LocalDate establishedDate;
        private String address;
        private int bookCount;

        public static SimpleResponse fromEntity(Publisher publisher) {
            return SimpleResponse.builder()
                    .id(publisher.getId())
                    .name(publisher.getName())
                    .establishedDate(publisher.getEstablishedDate())
                    .address(publisher.getAddress())
                    .build();
        }

        public static SimpleResponse fromEntityWithCount(Publisher publisher) {
            int bookCount = publisher.getBooks().size();
            return SimpleResponse.builder()
                    .id(publisher.getId())
                    .name(publisher.getName())
                    .establishedDate(publisher.getEstablishedDate())
                    .address(publisher.getAddress())
                    .bookCount(bookCount)
                    .build();
        }
    }
}
