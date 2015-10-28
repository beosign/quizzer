package de.beosign.quizzer.validator;

import java.util.Locale;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.logging.Log;
import de.beosign.quizzer.model.Question;

public class QuizzerMessageInterpolator implements MessageInterpolator {
    private static final Logger LOGGER = Log.logger();

    public QuizzerMessageInterpolator() {
        LOGGER.debug("");
    }

    @Override
    public String interpolate(String msg, Context ctx) {
        LOGGER.debug(msg + ", " + ctx);
        return interpolate(msg, ctx, Locale.getDefault());
    }

    @Override
    public String interpolate(String msg, Context ctx, Locale locale) {
        LOGGER.debug(locale + ", " + msg + ", " + ctx);

        MessageInterpolator messageInterpolator = Validation.byDefaultProvider().configure().getDefaultMessageInterpolator();
        String interpolatedMessage = messageInterpolator.interpolate(msg, ctx, locale);

        if (ctx.getConstraintDescriptor().getAnnotation().annotationType().equals(QuestionTypePoints.class)) {
            Question q = (Question) ctx.getValidatedValue();
            interpolatedMessage = interpolatedMessage.replace("{type}", q.getType().toString());
            interpolatedMessage = interpolatedMessage.replace("{minPoints}", "???");
        }
        return interpolatedMessage;
    }

}
