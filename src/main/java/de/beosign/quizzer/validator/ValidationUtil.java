package de.beosign.quizzer.validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.Logger;

/**
 * JSF is not able to validate a whole object wrt. Bean Validation (JSR-303). Therefore, it is necessary to manually validate the
 * object.
 * 
 * @author Florian Dahlmanns
 */
@ApplicationScoped
public class ValidationUtil implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Logger logger;

    @Inject
    private Validator validator;

    @Inject
    private FacesContext facesContext;

    public ValidationUtil() {
    }

    protected ValidationUtil(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    @PostConstruct
    private void init() {
        logger.debug("Validator: " + validator);
    }

    private static <T> List<FacesMessage> addValidationErrorsToFacesContext(T o, FacesContext facesContext) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> validationErrors = validator.validate(o);

        return addValidationErrorsToFacesContext(validationErrors, facesContext);
    }

    private static <T> List<FacesMessage> addValidationErrorsToFacesContext(Set<ConstraintViolation<T>> violations, FacesContext facesContext) {
        List<FacesMessage> facesMessages = new ArrayList<>();

        for (ConstraintViolation<?> cv : violations) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, cv.getMessage(), "");
            facesMessages.add(fm);

            facesContext.addMessage(null, fm);
        }

        return facesMessages;

    }

    public <T> List<FacesMessage> addValidationErrorsToFacesContext(T o) {
        Set<ConstraintViolation<T>> validationErrors = validator.validate(o);

        return addValidationErrorsToFacesContext(validationErrors, facesContext);
    }

    public <T> List<FacesMessage> addValidationErrorsToFacesContext(Set<ConstraintViolation<T>> violations) {
        List<FacesMessage> facesMessages = new ArrayList<>();

        for (ConstraintViolation<?> cv : violations) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, cv.getMessage(), "");
            facesMessages.add(fm);

            facesContext.addMessage(null, fm);
        }

        return facesMessages;

    }
}
