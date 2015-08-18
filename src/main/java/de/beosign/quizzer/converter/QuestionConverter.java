package de.beosign.quizzer.converter;

import java.util.Optional;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.logging.log4j.Logger;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import de.beosign.quizzer.logging.Log;
import de.beosign.quizzer.model.Question;

/**
 * Converter for PrimeFaces' PickList ({@link DualListModel}). To retrieve the object, this converter searches both lists for the given String-based key, namely
 * {@link Question#getCode()}.
 * 
 * @author Florian Dahlmanns
 *
 */
@FacesConverter(value = "QuestionConverter")
public class QuestionConverter implements Converter {
    private static final Logger LOGGER = Log.logger();

    @Override
    public Question getAsObject(FacesContext context, UIComponent component, String value) {
        LOGGER.debug("component: {}, value: {}", component.getClass().getName(), value);
        if (component instanceof PickList) {
            PickList pl = (PickList) component;

            @SuppressWarnings("unchecked")
            DualListModel<Question> dlm = (DualListModel<Question>) pl.getValue();
            Optional<Question> question = dlm.getSource().stream().filter(q -> q.getCode().equals(value)).findFirst();
            if (!question.isPresent()) {
                question = dlm.getTarget().stream().filter(q -> q.getCode().equals(value)).findFirst();
            }

            return question.orElse(null);

        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Question q = (Question) value;
        LOGGER.debug("component: {}, value: {}", component.getClass().getName(), value);
        return q.getCode();
    }

}
