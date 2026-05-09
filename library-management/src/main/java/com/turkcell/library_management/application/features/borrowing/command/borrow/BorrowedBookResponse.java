package com.turkcell.library_management.application.features.borrowing.command.borrow;

import java.time.LocalDate;
import java.util.UUID;

public record BorrowedBookResponse(UUID id, UUID bookId, UUID memberId, LocalDate borrowDate, LocalDate dueDate) {}
