package de.beosign.quizzer.logging;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.logging.log4j.Logger;

public class TraceInterceptor {
    @Inject
    private Logger logger;

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        String className = ctx.getTarget().getClass().getName();
        String method = ctx.getMethod().getName();
        Object[] params = ctx.getParameters();

        logger.trace("*** DefaultInterceptor intercepting {}::{}({})", className, method, params);
        try {
            return ctx.proceed();
        } finally {
            logger.trace("*** DefaultInterceptor exiting " + ctx.getMethod().getName());
        }
    }
}
