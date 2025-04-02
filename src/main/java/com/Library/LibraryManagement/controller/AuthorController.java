package com.Library.LibraryManagement.controller;

import com.Library.LibraryManagement.model.dto.AuthorRequest;
import com.Library.LibraryManagement.model.dto.AuthorResponse;
import com.Library.LibraryManagement.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Author Management", description = "Endpoints for managing authors")
@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @Operation(summary = "Create a new author")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponse createAuthor(@Valid @RequestBody AuthorRequest authorRequest) {
        return authorService.createAuthor(authorRequest);
    }

    @Operation(summary = "Get author by ID")
    @GetMapping("/{id}")
    public AuthorResponse getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @Operation(summary = "List all authors")
    @GetMapping
    public List<AuthorResponse> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @Operation(summary = "Update author details")
    @PutMapping("/{id}")
    public AuthorResponse updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequest authorRequest
    ) {
        return authorService.updateAuthor(id, authorRequest);
    }

    @Operation(summary = "Delete an author")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}