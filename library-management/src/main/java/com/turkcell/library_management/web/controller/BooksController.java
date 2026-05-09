package com.turkcell.library_management.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.library_management.application.features.book.command.create.CreateBookCommand;
import com.turkcell.library_management.application.features.book.command.create.CreatedBookResponse;
import com.turkcell.library_management.application.features.book.query.getall.GetAllBooksQuery;
import com.turkcell.library_management.application.features.book.query.getall.GetAllBooksResponse;
import com.turkcell.library_management.core.mediator.Mediator;

import jakarta.validation.Valid;

@RequestMapping("/api/books")
@RestController
public class BooksController {
    private final Mediator mediator;

    public BooksController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping
    public CreatedBookResponse create(@RequestBody @Valid CreateBookCommand command) {
        return mediator.send(command);
    }

    @GetMapping
    public Page<GetAllBooksResponse> getAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return mediator.send(new GetAllBooksQuery(pageNumber, pageSize));
    }
}
