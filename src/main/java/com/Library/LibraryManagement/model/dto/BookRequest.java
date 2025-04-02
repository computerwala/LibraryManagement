package com.Library.LibraryManagement.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "ISBN is mandatory")
    private String isbn;

    @NotNull(message = "Author ID is mandatory")
    private Long authorId;

    @NotNull(message = "Published date is mandatory")
    @PastOrPresent(message = "Published date must be in the past or present")
    private LocalDate publishedDate;
}