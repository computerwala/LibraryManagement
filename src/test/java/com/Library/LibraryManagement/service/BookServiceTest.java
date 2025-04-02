package com.Library.LibraryManagement.service;

import com.Library.LibraryManagement.exception.ResourceNotFoundException;
import com.Library.LibraryManagement.model.Author;
import com.Library.LibraryManagement.model.Book;
import com.Library.LibraryManagement.model.dto.BookRequest;
import com.Library.LibraryManagement.model.dto.BookResponse;
import com.Library.LibraryManagement.repository.AuthorRepository;
import com.Library.LibraryManagement.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void createBook_ValidRequest_CreatesBook() {
        // Arrange
        BookRequest request = new BookRequest();
        request.setTitle("Test Book");
        request.setIsbn("123");
        request.setAuthorId(1L);
        request.setPublishedDate(LocalDate.now());

        // Use builder pattern instead of constructor
        Author author = Author.builder()
                .id(1L)
                .name("Author")
                .bio("Bio")
                .build();

        Book savedBook = Book.builder()
                .id(1L)
                .title(request.getTitle())
                .isbn(request.getIsbn())
                .author(author)
                .publishedDate(request.getPublishedDate())
                .build();

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        // Act
        BookResponse response = bookService.createBook(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getAuthorId());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void createBook_InvalidAuthorId_ThrowsException() {
        // Arrange
        BookRequest request = new BookRequest();
        request.setAuthorId(99L);
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                bookService.createBook(request)
        );
    }
}