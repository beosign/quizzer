package de.beosign.quizzer.service.ejbtest;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.Logger;

@Stateless
@LocalBean
// @LocalBean is needed for being able to inject the concrete type
// The LocalInterface has the @LocalAnnotation marling at as the interface taht makes up the Local-Business-Interface-View
// See also http://stackoverflow.com/questions/10889563/ejb-3-1-localbean-vs-no-annotation
public class LocalBusinessBean implements LocalInterface, NoBusiness {
    @Inject
    private Logger logger;

    @Override
    public void callMeNoBusiness() {
        logger.debug("call");

    }

    @Override
    public void localBusiness() {
        logger.debug("call local interface method");

    }

}
