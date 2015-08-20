package de.beosign.quizzer.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = TimeSpanValidator.class)
@Documented
public @interface TimeSpan {
    String message() default "{duration.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long min() default 0;

    long max();

    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
