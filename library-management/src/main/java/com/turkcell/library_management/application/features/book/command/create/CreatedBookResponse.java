package com.turkcell.library_management.application.features.book.command.create;

import java.util.UUID;

public record CreatedBookResponse(UUID id, String isbn, String name, UUID categoryId, UUID publisherId) {}
