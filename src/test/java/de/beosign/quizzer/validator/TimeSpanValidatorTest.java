package de.beosign.quizzer.validator;

import static org.junit.Assert.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Test;

import de.beosign.quizzer.validator.TimeSpan;

public class TimeSpanValidatorTest {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void timeSpanValidationMediumValid() {
        TimeSpanClass ts = new TimeSpanClass(Duration.ofMinutes(5));
        assertTrue("Validation failed for medium value", validator.validate(ts).isEmpty());
    }

    @Test
    public void timeSpanValidationLowerBoundValid() {
        TimeSpanClass ts = new TimeSpanClass(Duration.ofMinutes(2));

        assertTrue("Validation failed for lower bound value", validator.validate(ts).isEmpty());
    }

    @Test
    public void timeSpanValidationUpperBoundValid() {
        TimeSpanClass ts = new TimeSpanClass(Duration.ofMinutes(10));

        assertTrue("Validation failed for upper bound value", validator.validate(ts).isEmpty());
    }

    @Test
    public void timeSpanValidationTooLowNotValid() {
        TimeSpanClass ts = new TimeSpanClass(Duration.ofMinutes(1));

        assertTrue("Validation passed for a value that is too low", validator.validate(ts).size() == 1);
    }

    @Test
    public void timeSpanValidationTooHighNotValid() {
        TimeSpanClass ts = new TimeSpanClass(Duration.ofMinutes(11));

        assertTrue("Validation passed for a value that is too high", validator.validate(ts).size() == 1);
    }

    @Test
    public void timeSpanValidationNegativeNotValid() {
        TimeSpanClass ts = new TimeSpanClass(Duration.ofMinutes(-3));

        assertTrue("Validation passed for a negative value", validator.validate(ts).size() == 1);
    }

    @Test
    public void timeSpanValidationZeroNotValid() {
        TimeSpanClass ts = new TimeSpanClass(Duration.ofMinutes(11));

        assertTrue("Validation passed for 0", validator.validate(ts).size() == 1);
    }

    private static class TimeSpanClass {
        @TimeSpan(min = 2, max = 10, timeUnit = TimeUnit.MINUTES)
        private final Duration timeSpan;

        private TimeSpanClass(Duration timeSpan) {
            this.timeSpan = timeSpan;
        }

    }

}
