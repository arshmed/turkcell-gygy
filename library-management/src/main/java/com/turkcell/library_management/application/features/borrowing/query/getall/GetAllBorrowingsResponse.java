package com.turkcell.library_management.application.features.borrowing.query.getall;

import java.time.LocalDate;
import java.util.UUID;

public record GetAllBorrowingsResponse(
    UUID id, String bookName, String memberName, String staffName,
    LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate, String status
) {}
