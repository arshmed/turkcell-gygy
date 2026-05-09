package com.turkcell.library_management.core.performance;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.turkcell.library_management.core.mediator.pipeline.PipelineBehavior;
import com.turkcell.library_management.core.mediator.pipeline.RequestHandlerDelegate;

@Component
@Order(18)
public class PerformanceBehavior implements PipelineBehavior {

    private static final long THRESHOLD_MS = 3000;

    @Override
    public <R> R handle(Object request, RequestHandlerDelegate<R> next) {
        long start = System.currentTimeMillis();

        R response = next.invoke();

        long duration = System.currentTimeMillis() - start;

        if (duration > THRESHOLD_MS) {
            System.out.println("[WARN] Performance: " + request.getClass().getSimpleName()
                    + " completed in " + duration + "ms (threshold: " + THRESHOLD_MS + "ms)");
        } else {
            System.out.println("[PERF] " + request.getClass().getSimpleName()
                    + " completed in " + duration + "ms");
        }

        return response;
    }
}
