package com.turkcell.library_management.application.features.user.command.register;

import java.util.UUID;

public record RegisterResponse(UUID id, String email) {}
