package com.turkcell.library_management.application.features.author.command.create;

import java.util.UUID;

public record CreatedAuthorResponse(UUID id, String firstName, String lastName) {}
