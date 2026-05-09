package com.turkcell.library_management.application.features.category.command.create;

import java.util.UUID;

public record CreatedCategoryResponse(UUID id, String name, String description) {}
