package com.Library.LibraryManagement.controller;

import com.Library.LibraryManagement.exception.GlobalExceptionHandler;
import com.Library.LibraryManagement.model.dto.AuthorRequest;
import com.Library.LibraryManagement.model.dto.AuthorResponse;
import com.Library.LibraryManagement.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthorControllerIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController)
                .setControllerAdvice(new GlobalExceptionHandler()) // Add exception handler
                .build();
    }

    @Test
    void createAuthor_ValidInput_ReturnsCreated() throws Exception {
        AuthorRequest request = new AuthorRequest("Test Author", "Bio");
        AuthorResponse response = new AuthorResponse(1L, "Test Author", "Bio");

        given(authorService.createAuthor(any(AuthorRequest.class))).willReturn(response);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Author"));
    }

    // Other test methods remain the same...
}