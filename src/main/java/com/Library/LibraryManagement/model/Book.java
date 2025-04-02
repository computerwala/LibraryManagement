package com.Library.LibraryManagement.model;

import com.Library.LibraryManagement.model.dto.BookRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Book title is mandatory")
    private String title;

    @NotBlank(message = "ISBN is mandatory")
    private String isbn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull(message = "Author must be specified")
    private Author author;

    @NotNull(message = "date is mandatory")
    @PastOrPresent(message = "date cannot be in the future")
    private LocalDate publishedDate;

    public void updateFromRequest(BookRequest request, Author author) {
        if (request == null || author == null) {
            throw new IllegalArgumentException("BookRequest and Author cannot be null");
        }
        this.title = request.getTitle();
        this.isbn = request.getIsbn();
        this.author = author;
        this.publishedDate = request.getPublishedDate();
    }
}