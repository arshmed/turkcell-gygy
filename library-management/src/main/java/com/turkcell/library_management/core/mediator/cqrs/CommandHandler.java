package com.turkcell.library_management.core.mediator.cqrs;

public interface CommandHandler<C extends Command<R>, R> {
    R handle(C command);
}
