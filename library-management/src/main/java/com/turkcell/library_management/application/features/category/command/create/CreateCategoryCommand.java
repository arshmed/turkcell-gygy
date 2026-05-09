package com.turkcell.library_management.application.features.category.command.create;

import org.hibernate.validator.constraints.Length;

import com.turkcell.library_management.core.mediator.cqrs.Command;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryCommand(
    @NotBlank @Length(min = 3, max = 100) String name,
    String description
) implements Command<CreatedCategoryResponse> {}
