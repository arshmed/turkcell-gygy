package com.turkcell.library_management.core.logging;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.turkcell.library_management.core.mediator.pipeline.PipelineBehavior;
import com.turkcell.library_management.core.mediator.pipeline.RequestHandlerDelegate;

@Component
@Order(20)
public class LoggingBehavior implements PipelineBehavior {

    @Override
    public boolean supports(Object request) {
        return !(request instanceof NotLoggableRequest);
    }

    @Override
    public <R> R handle(Object request, RequestHandlerDelegate<R> next) {
        System.out.println("[LOG] Request: " + request.getClass().getSimpleName() + " | Data: " + request);

        R response = next.invoke();

        System.out.println("[LOG] Response: " + response);
        return response;
    }
}
