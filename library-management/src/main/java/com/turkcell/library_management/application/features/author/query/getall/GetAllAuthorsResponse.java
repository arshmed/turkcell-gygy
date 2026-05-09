package com.turkcell.library_management.application.features.author.query.getall;

import java.util.UUID;

public record GetAllAuthorsResponse(UUID id, String firstName, String lastName, String biography) {}
