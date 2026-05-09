package com.turkcell.library_management.application.features.book.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.application.features.book.command.create.CreateBookCommand;
import com.turkcell.library_management.application.features.book.command.create.CreatedBookResponse;
import com.turkcell.library_management.application.features.book.query.getall.GetAllBooksResponse;
import com.turkcell.library_management.domain.Author;
import com.turkcell.library_management.domain.Book;
import com.turkcell.library_management.domain.Category;
import com.turkcell.library_management.domain.Publisher;
import com.turkcell.library_management.persistence.repository.CategoryRepository;
import com.turkcell.library_management.persistence.repository.PublisherRepository;

@Component
public class BookMapper {
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    public BookMapper(CategoryRepository categoryRepository, PublisherRepository publisherRepository) {
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
    }

    public Book bookFromCreateCommand(CreateBookCommand command) {
        Book book = new Book();
        book.setIsbn(command.isbn());
        book.setName(command.name());

        Category category = categoryRepository.findById(command.categoryId())
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"));
        book.setCategory(category);

        Publisher publisher = publisherRepository.findById(command.publisherId())
                .orElseThrow(() -> new RuntimeException("Yayınevi bulunamadı"));
        book.setPublisher(publisher);

        book.setPublishYear(command.publishYear());
        book.setPageCount(command.pageCount());
        book.setTotalCopies(command.totalCopies() > 0 ? command.totalCopies() : 1);
        book.setAvailableCopies(command.totalCopies() > 0 ? command.totalCopies() : 1);

        return book;
    }

    public CreatedBookResponse createdBookResponseFromBook(Book book) {
        return new CreatedBookResponse(
            book.getId(), book.getIsbn(), book.getName(),
            book.getCategory().getId(), book.getPublisher().getId()
        );
    }

    public GetAllBooksResponse getAllBooksResponseFromBook(Book book) {
        return new GetAllBooksResponse(
            book.getId(), book.getIsbn(), book.getName(),
            book.getCategory().getId(), book.getCategory().getName(),
            book.getPublisher().getId(), book.getPublisher().getName(),
            book.getPublishYear(), book.getPageCount(),
            book.getTotalCopies(), book.getAvailableCopies(),
            book.getAuthors() != null
                ? book.getAuthors().stream().map(a -> a.getFirstName() + " " + a.getLastName()).collect(Collectors.toSet())
                : null
        );
    }
}
