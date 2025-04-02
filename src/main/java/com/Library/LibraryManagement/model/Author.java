package com.Library.LibraryManagement.model;

import com.Library.LibraryManagement.model.dto.AuthorRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Author name is mandatory")
    private String name;

    private String bio;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Book> books = new ArrayList<>();

    public void updateFromRequest(AuthorRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("AuthorRequest cannot be null");
        }
        this.name = request.getName();
        this.bio = request.getBio();
    }

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }
}