package de.beosign.quizzer.service.ejbtest;

/*
 *  This interface cannot be used for injection as it is not annotated with @Local. It is only possible to inject against this interface if the EJB implementing this interface references it with @Local(NoBusiness.class) 
 */
public interface NoBusiness {
    void callMeNoBusiness();
}
