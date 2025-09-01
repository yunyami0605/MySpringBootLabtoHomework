package com.homework1.MySpringbootLab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate establishedDate;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "publisher", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonIgnore
    private List<Book> books;

    public void addBook(Book book){
        //
    }

    public void removeBook(Book book){
        //
    }
}
