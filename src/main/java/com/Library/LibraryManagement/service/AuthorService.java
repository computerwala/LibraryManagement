package com.Library.LibraryManagement.service;

import com.Library.LibraryManagement.exception.ResourceNotFoundException;
import com.Library.LibraryManagement.model.Author;
import com.Library.LibraryManagement.model.dto.AuthorRequest;
import com.Library.LibraryManagement.model.dto.AuthorResponse;
import com.Library.LibraryManagement.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorResponse createAuthor(AuthorRequest authorRequest) {
        // Null check added
        if (authorRequest == null) {
            throw new IllegalArgumentException("AuthorRequest cannot be null");
        }

        Author author = new Author();
        author.updateFromRequest(authorRequest);
        Author savedAuthor = authorRepository.save(author);
        return convertToResponse(savedAuthor);
    }

    @Transactional(readOnly = true)
    public AuthorResponse getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return convertToResponse(author);
    }

    @Transactional(readOnly = true)
    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public AuthorResponse updateAuthor(Long id, AuthorRequest authorRequest) {
        // Null check added
        if (authorRequest == null) {
            throw new IllegalArgumentException("AuthorRequest cannot be null");
        }

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        author.updateFromRequest(authorRequest);
        Author updatedAuthor = authorRepository.save(author);
        return convertToResponse(updatedAuthor);
    }

    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        authorRepository.delete(author);
    }

    private AuthorResponse convertToResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .bio(author.getBio())
                .build();
    }
}