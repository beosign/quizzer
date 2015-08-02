package de.beosign.quizzer.logging;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.logging.log4j.Logger;

@ApplicationScoped
public class LoggerProducer {
    private static final Logger LOGGER = Log.logger(LoggerProducer.class);

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        LOGGER.debug("Getting logger instance for " + injectionPoint + " - " + injectionPoint.getBean());
        return Log.logger(injectionPoint.getMember().getDeclaringClass());

    }

}
