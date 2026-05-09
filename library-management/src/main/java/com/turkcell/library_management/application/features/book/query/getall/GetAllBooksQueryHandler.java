package com.turkcell.library_management.application.features.book.query.getall;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.turkcell.library_management.application.features.book.mapper.BookMapper;
import com.turkcell.library_management.core.mediator.cqrs.QueryHandler;
import com.turkcell.library_management.domain.Book;
import com.turkcell.library_management.persistence.repository.BookRepository;

@Component
public class GetAllBooksQueryHandler implements QueryHandler<GetAllBooksQuery, Page<GetAllBooksResponse>> {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public GetAllBooksQueryHandler(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public Page<GetAllBooksResponse> handle(GetAllBooksQuery query) {
        Pageable pageable = PageRequest.of(query.pageNumber(), query.pageSize());
        Page<Book> books = bookRepository.findAll(pageable);
        return books.map(bookMapper::getAllBooksResponseFromBook);
    }
}
