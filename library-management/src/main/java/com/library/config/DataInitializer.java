package com.library.config;

import com.library.entity.Book;
import com.library.entity.Category;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            // Create sample categories
            Category fiction = Category.builder()
                    .name("Fiction")
                    .description("Fictional literature and novels")
                    .build();

            Category science = Category.builder()
                    .name("Science & Technology")
                    .description("Books on science, technology, and engineering")
                    .build();

            Category history = Category.builder()
                    .name("History")
                    .description("Historical events and biographies")
                    .build();

            categoryRepository.save(fiction);
            categoryRepository.save(science);
            categoryRepository.save(history);

            // Create sample books
            Book book1 = Book.builder()
                    .bookName("The Great Gatsby")
                    .author("F. Scott Fitzgerald")
                    .isbn("978-0-7432-7356-5")
                    .publishedDate(LocalDate.of(1925, 4, 10))
                    .category(fiction)
                    .build();

            Book book2 = Book.builder()
                    .bookName("Clean Code")
                    .author("Robert C. Martin")
                    .isbn("978-0-13-235088-4")
                    .publishedDate(LocalDate.of(2008, 8, 1))
                    .category(science)
                    .build();

            Book book3 = Book.builder()
                    .bookName("Sapiens")
                    .author("Yuval Noah Harari")
                    .isbn("978-0-06-231609-7")
                    .publishedDate(LocalDate.of(2011, 1, 1))
                    .category(history)
                    .build();

            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);

            System.out.println("✅ Sample data initialized successfully!");
        }
    }
}
