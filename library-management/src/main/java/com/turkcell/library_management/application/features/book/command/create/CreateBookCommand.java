package com.turkcell.library_management.application.features.book.command.create;

import java.util.Set;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.turkcell.library_management.core.mediator.cqrs.Command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBookCommand(
    @NotBlank @Length(min = 10, max = 13) String isbn,
    @NotBlank @Length(min = 1, max = 255) String name,
    @NotNull UUID categoryId,
    @NotNull UUID publisherId,
    Integer publishYear,
    Integer pageCount,
    int totalCopies,
    Set<UUID> authorIds
) implements Command<CreatedBookResponse> {}
