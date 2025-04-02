package com.Library.LibraryManagement.controller;

import com.Library.LibraryManagement.model.dto.BookRequest;
import com.Library.LibraryManagement.model.dto.BookResponse;
import com.Library.LibraryManagement.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createBook_ValidInput_ReturnsCreated() throws Exception {
        // Arrange
        BookRequest request = new BookRequest("Valid Book", "123", 1L, LocalDate.now());
        BookResponse response = new BookResponse(1L, "Valid Book", "123", 1L, "Author", LocalDate.now());

        given(bookService.createBook(any(BookRequest.class))).willReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Valid Book"));
    }

    @Test
    void createBook_InvalidDate_ReturnsBadRequest() throws Exception {
        // Arrange
        BookRequest invalidRequest = new BookRequest("Book", "123", 1L, LocalDate.now().plusDays(1));

        // Act & Assert
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("publishedDate: Published date must be in the past or present"));
    }
}