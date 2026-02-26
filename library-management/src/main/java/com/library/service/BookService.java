package com.library.service;

import com.library.dto.BookDTO;
import com.library.entity.Book;
import com.library.entity.Category;
import com.library.exception.DuplicateResourceException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        Book book = findBookById(id);
        return toDTO(book);
    }

    public List<BookDTO> getBooksByCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with ID: " + categoryId);
        }
        return bookRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> searchBooks(String name, Long categoryId) {
        List<Book> books;
        if (categoryId != null && StringUtils.hasText(name)) {
            books = bookRepository.findByCategoryIdAndBookNameContainingIgnoreCase(categoryId, name);
        } else if (StringUtils.hasText(name)) {
            books = bookRepository.findByBookNameContainingIgnoreCase(name);
        } else if (categoryId != null) {
            books = bookRepository.findByCategoryId(categoryId);
        } else {
            books = bookRepository.findAll();
        }
        return books.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public BookDTO createBook(BookDTO dto) {
        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new DuplicateResourceException("ISBN '" + dto.getIsbn() + "' already exists.");
        }
        Category category = findCategoryById(dto.getCategoryId());
        Book book = Book.builder()
                .bookName(dto.getBookName())
                .author(dto.getAuthor())
                .isbn(dto.getIsbn())
                .publishedDate(dto.getPublishedDate())
                .category(category)
                .build();
        Book saved = bookRepository.save(book);
        return toDTO(saved);
    }

    @Transactional
    public BookDTO updateBook(Long id, BookDTO dto) {
        Book book = findBookById(id);

        if (bookRepository.existsByIsbnAndIdNot(dto.getIsbn(), id)) {
            throw new DuplicateResourceException("ISBN '" + dto.getIsbn() + "' already exists.");
        }

        Category category = findCategoryById(dto.getCategoryId());

        book.setBookName(dto.getBookName());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPublishedDate(dto.getPublishedDate());
        book.setCategory(category);

        Book saved = bookRepository.save(book);
        return toDTO(saved);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = findBookById(id);
        bookRepository.delete(book);
    }

    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
    }

    private BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publishedDate(book.getPublishedDate())
                .categoryId(book.getCategory().getId())
                .categoryName(book.getCategory().getName())
                .build();
    }
}
