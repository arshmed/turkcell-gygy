package com.turkcell.library_management.core.security.authorization;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.turkcell.library_management.core.mediator.pipeline.PipelineBehavior;
import com.turkcell.library_management.core.mediator.pipeline.RequestHandlerDelegate;

@Component
@Order(10)
public class AuthorizationBehavior implements PipelineBehavior {

    @Override
    public <R> R handle(Object request, RequestHandlerDelegate<R> next) {
        System.out.println("AuthorizationBehavior çalışıyor");
        return next.invoke();
    }
}
