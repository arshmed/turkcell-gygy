package com.turkcell.library_management.application.features.book.rule;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.domain.Book;
import com.turkcell.library_management.persistence.repository.BookRepository;

@Component
public class BookBusinessRules {
    private final BookRepository bookRepository;

    public BookBusinessRules(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void bookWithSameIsbnMustNotExist(String isbn) {
        Book book = bookRepository.findByIsbn(isbn).orElse(null);
        if (book != null)
            throw new RuntimeException("Aynı ISBN ile 2 kitap eklenemez");
    }
}
