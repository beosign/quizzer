package de.beosign.quizzer.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides access to loggers. Use this instead of declaring a logger instance in each class.
 * 
 * @author Florian Dahlmanns
 */
public final class Log {

    /**
     * Private constructor.
     */
    private Log() {
    }

    /**
     * Returns a logger where the logger name is determined by looking at the stacktrace.
     * 
     * @return logger
     */
    public static Logger logger() {
        String callingClassName = Thread.currentThread().getStackTrace()[2].getClassName();
        return LogManager.getLogger(callingClassName);
    }

    /**
     * Returns a logger where the logger name is determined by the given class.
     * 
     * @return logger
     */
    public static Logger logger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

}
