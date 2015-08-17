package de.beosign.quizzer.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * JSF is not able to validate a whole object wrt. Bean Validation (JSR-303). Therefore, it is necessary to manually validate the
 * object.
 * 
 * @author Florian Dahlmanns
 *
 */
public class ValidationUtil {

    public static <T> List<FacesMessage> addValidationErrorsToFacesContext(T o, FacesContext facesContext) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> validationErrors = validator.validate(o);

        return addValidationErrorsToFacesContext(validationErrors, facesContext);
    }

    public static <T> List<FacesMessage> addValidationErrorsToFacesContext(Set<ConstraintViolation<T>> violations, FacesContext facesContext) {
        List<FacesMessage> facesMessages = new ArrayList<>();

        for (ConstraintViolation<?> cv : violations) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, cv.getMessage(), "");
            facesMessages.add(fm);

            facesContext.addMessage(null, fm);
        }

        return facesMessages;

    }
}
