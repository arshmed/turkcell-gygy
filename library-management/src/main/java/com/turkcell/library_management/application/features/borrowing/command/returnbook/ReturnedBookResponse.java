package com.turkcell.library_management.application.features.borrowing.command.returnbook;

import java.time.LocalDate;
import java.util.UUID;

public record ReturnedBookResponse(UUID id, UUID bookId, UUID memberId, LocalDate returnDate, String status) {}
