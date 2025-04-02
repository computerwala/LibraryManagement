package com.Library.LibraryManagement.service;

import com.Library.LibraryManagement.exception.ResourceNotFoundException;
import com.Library.LibraryManagement.model.Author;
import com.Library.LibraryManagement.model.Book;
import com.Library.LibraryManagement.model.dto.BookRequest;
import com.Library.LibraryManagement.model.dto.BookResponse;
import com.Library.LibraryManagement.repository.AuthorRepository;
import com.Library.LibraryManagement.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookResponse createBook(BookRequest bookRequest) {
        if (bookRequest == null) {
            throw new IllegalArgumentException("BookRequest cannot be null");
        }

        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + bookRequest.getAuthorId()));

        Book book = new Book();
        book.updateFromRequest(bookRequest, author);
        Book savedBook = bookRepository.save(book);
        return convertToResponse(savedBook);
    }

    @Transactional(readOnly = true)
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return convertToResponse(book);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        if (bookRequest == null) {
            throw new IllegalArgumentException("BookRequest cannot be null");
        }

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + bookRequest.getAuthorId()));

        book.updateFromRequest(bookRequest, author);
        Book updatedBook = bookRepository.save(book);
        return convertToResponse(updatedBook);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        bookRepository.delete(book);
    }

    private BookResponse convertToResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .authorId(book.getAuthor().getId())
                .authorName(book.getAuthor().getName())
                .publishedDate(book.getPublishedDate())
                .build();
    }
}