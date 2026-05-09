package com.turkcell.library_management.application.features.book.query.getall;

import java.util.Set;
import java.util.UUID;

public record GetAllBooksResponse(
    UUID id, String isbn, String name,
    UUID categoryId, String categoryName,
    UUID publisherId, String publisherName,
    Integer publishYear, Integer pageCount,
    int totalCopies, int availableCopies,
    Set<String> authorNames
) {}
