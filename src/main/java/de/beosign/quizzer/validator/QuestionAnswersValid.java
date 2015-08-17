package de.beosign.quizzer.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = QuestionAnswersValidator.class)
@Documented
public @interface QuestionAnswersValid {
    String message() default "{question.answers.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
