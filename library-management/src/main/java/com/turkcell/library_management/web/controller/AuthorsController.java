package com.turkcell.library_management.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.library_management.application.features.author.command.create.CreateAuthorCommand;
import com.turkcell.library_management.application.features.author.command.create.CreatedAuthorResponse;
import com.turkcell.library_management.application.features.author.query.getall.GetAllAuthorsQuery;
import com.turkcell.library_management.application.features.author.query.getall.GetAllAuthorsResponse;
import com.turkcell.library_management.core.mediator.Mediator;

import jakarta.validation.Valid;

@RequestMapping("/api/authors")
@RestController
public class AuthorsController {
    private final Mediator mediator;

    public AuthorsController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping
    public CreatedAuthorResponse create(@RequestBody @Valid CreateAuthorCommand command) {
        return mediator.send(command);
    }

    @GetMapping
    public List<GetAllAuthorsResponse> getAll() {
        return mediator.send(new GetAllAuthorsQuery());
    }
}
