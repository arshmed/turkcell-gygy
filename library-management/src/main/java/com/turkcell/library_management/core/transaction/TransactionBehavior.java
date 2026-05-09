package com.turkcell.library_management.core.transaction;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import com.turkcell.library_management.core.mediator.cqrs.Command;
import com.turkcell.library_management.core.mediator.pipeline.PipelineBehavior;
import com.turkcell.library_management.core.mediator.pipeline.RequestHandlerDelegate;

@Component
@Order(15)
public class TransactionBehavior implements PipelineBehavior {

    private final TransactionTemplate transactionTemplate;

    public TransactionBehavior(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public <R> R handle(Object request, RequestHandlerDelegate<R> next) {
        if (request instanceof Command) {
            return transactionTemplate.execute(status -> next.invoke());
        }
        return transactionTemplate.execute(status -> next.invoke());
    }
}
