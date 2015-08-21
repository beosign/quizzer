package de.beosign.quizzer.util;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;

@SessionScoped
@Named
public class LocaleBean implements Serializable {
    private static final long serialVersionUID = 6480930477147438916L;

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

    private Locale locale;

    public void changeLocale(String locale) {
        logger.debug("Changing locale to {}", locale);
        this.locale = new Locale(locale);
    }

    public Locale getLocale() {
        if (locale == null) {
            locale = facesContext.getViewRoot().getLocale();
        }
        logger.debug("locale: {}", locale);
        return locale;
    }

    public void setLocale(Locale locale) {
        logger.debug("new locale: {}", locale);
        this.locale = locale;
    }
}
