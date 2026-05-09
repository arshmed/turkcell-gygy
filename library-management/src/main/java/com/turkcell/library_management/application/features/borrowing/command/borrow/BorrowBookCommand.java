package com.turkcell.library_management.application.features.borrowing.command.borrow;

import java.time.LocalDate;
import java.util.UUID;

import com.turkcell.library_management.core.mediator.cqrs.Command;

import jakarta.validation.constraints.NotNull;

public record BorrowBookCommand(
    @NotNull UUID bookId,
    @NotNull UUID memberId,
    @NotNull UUID staffId,
    @NotNull LocalDate dueDate
) implements Command<BorrowedBookResponse> {}
