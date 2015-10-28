package de.beosign.quizzer.validator;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = QuestionTypePointsValidator.class)
@Documented
public @interface QuestionTypePoints {
    String message() default "{question.points.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
