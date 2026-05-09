package com.turkcell.library_management.application.features.user.command.login;

import com.turkcell.library_management.core.logging.NotLoggableRequest;
import com.turkcell.library_management.core.mediator.cqrs.Command;

public record LoginCommand(String email, String password) implements Command<LoginResponse>, NotLoggableRequest {}
