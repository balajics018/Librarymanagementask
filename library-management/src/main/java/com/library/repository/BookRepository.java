package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, Long id);

    List<Book> findByCategoryId(Long categoryId);

    @Query("SELECT b FROM Book b WHERE LOWER(b.bookName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Book> findByBookNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT b FROM Book b WHERE b.category.id = :categoryId AND LOWER(b.bookName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Book> findByCategoryIdAndBookNameContainingIgnoreCase(@Param("categoryId") Long categoryId, @Param("name") String name);
}
