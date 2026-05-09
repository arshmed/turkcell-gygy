package com.turkcell.library_management.application.features.borrowing.command.returnbook;

import java.util.UUID;

import com.turkcell.library_management.core.mediator.cqrs.Command;

import jakarta.validation.constraints.NotNull;

public record ReturnBookCommand(
    @NotNull UUID borrowingId,
    @NotNull UUID staffId,
    String bookCondition,
    String notes
) implements Command<ReturnedBookResponse> {}
