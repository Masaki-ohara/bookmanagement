package com.example.bookmanagement.repository;

import java.util.List; 
import com.example.bookmanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(String name);
}
