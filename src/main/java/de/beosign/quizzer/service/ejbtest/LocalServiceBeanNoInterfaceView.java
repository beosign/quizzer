package de.beosign.quizzer.service.ejbtest;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.Logger;

@Stateless
@LocalBean
// Can be injected by concrete type only
public class LocalServiceBeanNoInterfaceView implements NoBusiness {
    @Inject
    private Logger logger;

    @Override
    public void callMeNoBusiness() {
        logger.debug("Called me with no interface view");
    }

}
