package de.beosign.quizzer.service.ejbtest;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.Logger;

// Can be injected by using this concrete type, see also http://stackoverflow.com/questions/10889563/ejb-3-1-localbean-vs-no-annotation
@Stateless
public class LocalServiceBeanNoInterface {
    @Inject
    private Logger logger;

    @PostConstruct
    private void init() {
        logger.debug("INIT");
    }

    public void callMe() {
        logger.debug("Called me");
    }
}
