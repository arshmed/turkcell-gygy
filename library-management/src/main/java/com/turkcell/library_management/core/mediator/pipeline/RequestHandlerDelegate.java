package com.turkcell.library_management.core.mediator.pipeline;

@FunctionalInterface
public interface RequestHandlerDelegate<R> {
    R invoke();
}
