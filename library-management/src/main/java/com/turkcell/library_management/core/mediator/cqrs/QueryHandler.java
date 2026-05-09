package com.turkcell.library_management.core.mediator.cqrs;

public interface QueryHandler<Q extends Query<R>, R> {
    R handle(Q query);
}
