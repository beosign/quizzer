package de.beosign.quizzer.logging;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 * Sets sessionid to MDC for logging purposes.
 * 
 * @author florian
 */
public class LoggingFilter implements Filter {
    private static final String USER_KEY = "USER";

    @Inject
    private Logger logger;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Initing " + LoggingFilter.class.getName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean foundSessionId = putIdIntoMDC(request);

        try {
            chain.doFilter(request, response);
        } finally {
            if (foundSessionId) {
                ThreadContext.remove(USER_KEY);
            }
        }

    }

    private boolean putIdIntoMDC(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        if (session != null) {
            String id = session.getId();
            if (!StringUtils.isEmpty(id)) {
                ThreadContext.put(USER_KEY, id);
            } else {
                ThreadContext.put(USER_KEY, "SERVER");
            }
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {
    }

}
