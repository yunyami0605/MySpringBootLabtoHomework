package com.homework1.MySpringbootLab.repository;

import com.homework1.MySpringbootLab.entity.Book;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//@AllArgsConstructor
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
//@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    Book book1;
    Book book2;

    @BeforeEach
    void testBeforeSetup(){
        book1 = Book.builder().title("스프링 부트 입문").author("홍길동").isbn("9788956746425").price(30000).publishDate(LocalDate.parse("2025-05-07")).build();
        book2 = Book.builder().title("JPA 프로그래밍").author("박둘리").isbn("9788956746432").price(35000).publishDate(LocalDate.parse("2025-04-30")).build();
    }

    @Test
    @DisplayName("도서 등록 테스트")
    void testCreateBook(){
        //
        Book saved = bookRepository.save(book1);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("스프링 부트 입문");
        assertThat(saved.getAuthor()).isEqualTo("홍길동");
        assertThat(saved.getIsbn()).isEqualTo("9788956746425");
        assertThat(saved.getPrice()).isEqualTo(30000);
    }

    @Test
    @DisplayName("ISBN으로 도서 조회 테스트")
    void testFindByIsbn(){
        //
        bookRepository.saveAll(List.of(book1, book2));

        Optional<Book> book1 = bookRepository.findByIsbn("9788956746425");
        Optional<Book> book2 = bookRepository.findByIsbn("9788956746432");

        assertThat(book1).isPresent();
        assertThat(book1.get().getTitle()).isEqualTo("스프링 부트 입문");

        assertThat(book2).isPresent();
        assertThat(book2.get().getTitle()).isEqualTo("JPA 프로그래밍");

    }

    @Test
    @DisplayName("저자명으로 도서 목록 조회 테스트")
    void testFindByAuthor(){
        //
        bookRepository.saveAll(List.of(book1, book2));

        List<Book> books = bookRepository.findByAuthor("홍길동");

        assertThat(books).hasSize(1);
        assertThat(books).extracting("title").containsExactlyInAnyOrder("스프링 부트 입문");
    }

    @Test
    @DisplayName("도서 수정 테스트")
    void testUpdateBook(){
        List<Book> books = bookRepository.saveAll(List.of(book1, book2));

        books.get(0).setPrice(40000);

        Book updatedBook = bookRepository.save(books.get(0));

        assertThat(updatedBook.getPrice()).isEqualTo(40000);
    }

    @Test
    @DisplayName("도서 삭제 테스트")
    void testDeleteBook(){
        List<Book> books = bookRepository.saveAll(List.of(book1, book2));
        Book book1 = books.get(0);

        Long _id = book1.getId();
        bookRepository.deleteById(_id);

        assertThat(bookRepository.findById(_id)).isNotPresent();
    }
}
