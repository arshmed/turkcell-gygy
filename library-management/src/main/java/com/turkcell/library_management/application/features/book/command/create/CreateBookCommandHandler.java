package com.turkcell.library_management.application.features.book.command.create;

import java.util.HashSet;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.application.features.book.mapper.BookMapper;
import com.turkcell.library_management.application.features.book.rule.BookBusinessRules;
import com.turkcell.library_management.core.mediator.cqrs.CommandHandler;
import com.turkcell.library_management.domain.Author;
import com.turkcell.library_management.domain.Book;
import com.turkcell.library_management.persistence.repository.AuthorRepository;
import com.turkcell.library_management.persistence.repository.BookRepository;

@Component
public class CreateBookCommandHandler implements CommandHandler<CreateBookCommand, CreatedBookResponse> {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookBusinessRules bookBusinessRules;
    private final BookMapper bookMapper;

    public CreateBookCommandHandler(BookRepository bookRepository, AuthorRepository authorRepository,
            BookBusinessRules bookBusinessRules, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookBusinessRules = bookBusinessRules;
        this.bookMapper = bookMapper;
    }

    @Override
    public CreatedBookResponse handle(CreateBookCommand command) {
        bookBusinessRules.bookWithSameIsbnMustNotExist(command.isbn());

        Book book = bookMapper.bookFromCreateCommand(command);

        if (command.authorIds() != null) {
            Iterable<Author> authors = authorRepository.findAllById(command.authorIds());
            book.setAuthors(new HashSet<>());
            for (Author author : authors) {
                book.getAuthors().add(author);
            }
        }

        bookRepository.save(book);

        return bookMapper.createdBookResponseFromBook(book);
    }
}
