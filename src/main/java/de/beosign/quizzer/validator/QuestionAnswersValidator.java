package de.beosign.quizzer.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.logging.Log;
import de.beosign.quizzer.model.Answer;
import de.beosign.quizzer.model.Question;

public class QuestionAnswersValidator implements ConstraintValidator<QuestionAnswersValid, List<Answer>> {
    private static final Logger LOGGER = Log.logger();

    @Override
    public void initialize(QuestionAnswersValid constraintAnnotation) {
        LOGGER.debug(constraintAnnotation);

    }

    @Override
    public boolean isValid(List<Answer> answers, ConstraintValidatorContext context) {
        if (answers.size() == 0) {
            return false;
        }

        Question question = answers.get(0).getQuestion();
        LOGGER.debug("checking question {}", question);
        switch (question.getType()) {
        case TEXT:
            return (question.getAnswers().size() == 1 && question.getAnswers().get(0).isCorrect() == true);
        case SINGLE:
            return question.getAnswers().stream().filter(a -> a.isCorrect()).count() == 1;
        case MULTIPLE:
            return question.getAnswers().stream().filter(a -> a.isCorrect()).count() >= 1;
        }
        return false;
    }

}
