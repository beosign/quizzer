package de.beosign.quizzer.util;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Dependent
public class Resources {

    @Produces
    @PersistenceContext(unitName = PersistenceHelper.PERSISTENCE_UNIT)
    private EntityManager entityManager;

    @RequestScoped
    @Produces
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    // @RequestScoped
    // @Produces
    // public HttpServletRequest getHttpServletRequest() {
    // return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    // }

}
