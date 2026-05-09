package com.turkcell.library_management.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.library_management.application.features.category.command.create.CreateCategoryCommand;
import com.turkcell.library_management.application.features.category.command.create.CreatedCategoryResponse;
import com.turkcell.library_management.application.features.category.query.getall.GetAllCategoriesQuery;
import com.turkcell.library_management.application.features.category.query.getall.GetAllCategoriesResponse;
import com.turkcell.library_management.core.mediator.Mediator;

import jakarta.validation.Valid;

@RequestMapping("/api/categories")
@RestController
public class CategoriesController {
    private final Mediator mediator;

    public CategoriesController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping
    public CreatedCategoryResponse create(@RequestBody @Valid CreateCategoryCommand command) {
        return mediator.send(command);
    }

    @GetMapping
    public Page<GetAllCategoriesResponse> getAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return mediator.send(new GetAllCategoriesQuery(pageNumber, pageSize));
    }
}
