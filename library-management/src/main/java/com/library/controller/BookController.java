package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.dto.BookDTO;
import com.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDTO>>> getAllBooks(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId) {

        List<BookDTO> books;
        if (name != null || categoryId != null) {
            books = bookService.searchBooks(name, categoryId);
        } else {
            books = bookService.getAllBooks();
        }
        return ResponseEntity.ok(ApiResponse.success("Books fetched successfully", books));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(ApiResponse.success("Book fetched successfully", book));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<BookDTO>>> getBooksByCategory(@PathVariable Long categoryId) {
        List<BookDTO> books = bookService.getBooksByCategory(categoryId);
        return ResponseEntity.ok(ApiResponse.success("Books fetched successfully", books));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookDTO>> createBook(@Valid @RequestBody BookDTO dto) {
        BookDTO created = bookService.createBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> updateBook(@PathVariable Long id,
                                                           @Valid @RequestBody BookDTO dto) {
        BookDTO updated = bookService.updateBook(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Book updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(ApiResponse.success("Book deleted successfully", null));
    }
}
