package de.beosign.quizzer.converter;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.logging.log4j.Logger;
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
@FacesConverter(value = DurationMinutesConverter.DURATION_MINUTES_CONVERTER)
public class DurationMinutesConverter implements Converter {
    public static final String DURATION_MINUTES_CONVERTER = "DurationMinutesConverter";

    private static final Logger LOGGER = Log.logger();

    @Override
    public Duration getAsObject(FacesContext context, UIComponent component, String value) {
        LOGGER.debug("component: {}, value: {}", component.getClass().getName(), value);

        if (value == null || value.isEmpty()) {
            return Duration.ZERO;
        } else {
            return Duration.ofMinutes(Long.valueOf(value));
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LOGGER.debug("component: {}, value: {}", component.getClass().getName(), value);
        Duration duration = (Duration) value;
        return String.valueOf(duration.get(ChronoUnit.SECONDS) / 60);
    }

}
