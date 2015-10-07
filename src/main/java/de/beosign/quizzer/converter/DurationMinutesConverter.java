package de.beosign.quizzer.converter;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;
import org.primefaces.model.DualListModel;

import de.beosign.quizzer.logging.Log;
import de.beosign.quizzer.model.Question;
import de.beosign.quizzer.util.FacesUtil;

/**
 * Converter for PrimeFaces' PickList ({@link DualListModel}). To retrieve the object, this converter searches both lists for the given String-based key, namely
 * {@link Question#getCode()}.
 * 
 * @author Florian Dahlmanns
 */
@ApplicationScoped
@Named
@FacesConverter(DurationMinutesConverter.DURATION_MINUTES_CONVERTER)
public class DurationMinutesConverter implements Converter {
    public static final String DURATION_MINUTES_CONVERTER = "DurationMinutesConverter";
    private static final Logger LOGGER = Log.logger();

    @Inject
    private Logger logger;

    @Override
    public Duration getAsObject(FacesContext context, UIComponent component, String value) {
        LOGGER.debug("component: {}, value: {}", component.getClass().getName(), value);

        if (value == null || value.isEmpty()) {
            return Duration.ZERO;
        } else {
            try {
                return Duration.ofMinutes(Long.parseLong(value));
            } catch (NumberFormatException e) {
                logger.info(e.getMessage(), e);
                FacesMessage fm = FacesUtil.getFacesMessage(context, FacesMessage.SEVERITY_ERROR, "converter.integral.invalid",
                        FacesUtil.getComponentLabel(context, component), value);
                throw new ConverterException(fm);
            }
        }

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LOGGER.debug("component: {}, value: {}", component.getClass().getName(), value);

        if (value == null) {
            return "0";
        }

        Duration duration = Duration.ofMinutes(0);
        if (value instanceof Duration) {

            duration = (Duration) value;
        } else {
            throw new IllegalArgumentException("The value to convert is not of type Duration, but: " + value.getClass().getName());
        }
        return String.valueOf(duration.get(ChronoUnit.SECONDS) / 60);
    }

    public String getConverterId() {
        return DURATION_MINUTES_CONVERTER;
    }

}
