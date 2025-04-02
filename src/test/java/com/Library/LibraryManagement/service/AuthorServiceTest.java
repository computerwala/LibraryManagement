package com.Library.LibraryManagement.service;

import com.Library.LibraryManagement.exception.ResourceNotFoundException;
import com.Library.LibraryManagement.model.Author;
import com.Library.LibraryManagement.model.dto.AuthorRequest;
import com.Library.LibraryManagement.model.dto.AuthorResponse;
import com.Library.LibraryManagement.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void createAuthor_ValidRequest_ReturnsResponse() {
        // Arrange
        AuthorRequest request = new AuthorRequest("Test Author", "Test Bio");
        Author savedAuthor = Author.builder()
                .id(1L)
                .name(request.getName())
                .bio(request.getBio())
                .build();

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        // Act
        AuthorResponse response = authorService.createAuthor(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Author", response.getName());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void getAuthorById_ExistingId_ReturnsAuthor() {
        // Arrange
        Long authorId = 1L;
        Author author = Author.builder()
                .id(authorId)
                .name("Existing Author")
                .build();

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        // Act
        AuthorResponse response = authorService.getAuthorById(authorId);

        // Assert
        assertNotNull(response);
        assertEquals(authorId, response.getId());
    }

    @Test
    void getAuthorById_NonExistingId_ThrowsException() {
        // Arrange
        Long authorId = 99L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                authorService.getAuthorById(authorId)
        );
    }

    @Test
    void updateAuthor_ValidRequest_UpdatesAuthor() {
        // Arrange
        Long authorId = 1L;
        AuthorRequest request = new AuthorRequest("Updated Author", "Updated Bio");
        Author existingAuthor = Author.builder()
                .id(authorId)
                .name("Original Author")
                .bio("Original Bio")
                .build();

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(any(Author.class))).thenReturn(existingAuthor);

        // Act
        AuthorResponse response = authorService.updateAuthor(authorId, request);

        // Assert
        assertEquals("Updated Author", response.getName());
        assertEquals("Updated Bio", response.getBio());
        verify(authorRepository, times(1)).save(existingAuthor);
    }
}