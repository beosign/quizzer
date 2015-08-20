package de.beosign.quizzer.validator;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimeSpanValidator implements ConstraintValidator<TimeSpan, Object> {
    private long minNanos;
    private long maxNanos;

    @Override
    public void initialize(TimeSpan timeSpanAnnotation) {

        minNanos = getNanoSeconds(timeSpanAnnotation.min(), timeSpanAnnotation.timeUnit());
        maxNanos = getNanoSeconds(timeSpanAnnotation.max(), timeSpanAnnotation.timeUnit());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            value = Duration.ZERO;
        }

        if (value instanceof Duration) {
            return isValid((Duration) value, context);
        } else if (value instanceof Long) {
            return isValid((Long) value, context);
        } else {
            throw new IllegalArgumentException("Type " + value.getClass().getName() + " is not a valid target for " + TimeSpan.class.getName() + " annotation");
        }

    }

    private boolean isValid(Duration duration, ConstraintValidatorContext context) {
        long nanos = duration.getSeconds() * 1000 * 1000 * 1000 + duration.getNano();
        return isValidNanos(nanos, context);
    }

    private boolean isValid(Long millis, ConstraintValidatorContext context) {
        return isValidNanos(millis * 1000 * 1000, context);
    }

    private boolean isValidNanos(long nanos, ConstraintValidatorContext context) {
        if (nanos >= minNanos && nanos <= maxNanos) {
            return true;
        } else {
            return false;
        }
    }

    private long getNanoSeconds(long amount, TimeUnit timeUnit) {
        switch (timeUnit) {
        case DAYS:
            return amount * 3600 * 24 * 1000 * 1000 * 1000;
        case HOURS:
            return amount * 3600 * 1000 * 1000 * 1000;
        case MINUTES:
            return amount * 60 * 1000 * 1000 * 1000;
        case SECONDS:
            return amount * 1000 * 1000 * 1000;
        case MILLISECONDS:
            return amount * 1000 * 1000;
        case MICROSECONDS:
            return amount * 1000;
        case NANOSECONDS:
            return amount;
        }
        throw new IllegalArgumentException("TimeUnit " + timeUnit + " is not valid");
    }

}
