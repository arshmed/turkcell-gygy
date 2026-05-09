package com.turkcell.library_management.application.features.author.command.create;

import org.hibernate.validator.constraints.Length;

import com.turkcell.library_management.core.mediator.cqrs.Command;

import jakarta.validation.constraints.NotBlank;

public record CreateAuthorCommand(
    @NotBlank @Length(min = 1, max = 100) String firstName,
    @NotBlank @Length(min = 1, max = 100) String lastName,
    String biography
) implements Command<CreatedAuthorResponse> {}
