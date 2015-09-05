package de.beosign.quizzer.validator;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.controller.QuestionController;
import de.beosign.quizzer.logging.Log;
import de.beosign.quizzer.model.Answer;
import de.beosign.quizzer.model.Question;
import de.beosign.quizzer.service.QuestionService;

public class QuestionAnswersValidator implements ConstraintValidator<QuestionAnswersValid, List<Answer>> {
    private static final Logger LOGGER = Log.logger();

    @Inject
    private QuestionController questionController;

    @Inject
    private QuestionService questionService;

    @EJB
    private QuestionService questionServiceEJB;

    @Override
    public void initialize(QuestionAnswersValid constraintAnnotation) {
        LOGGER.debug(constraintAnnotation);

    }

    @Override
    public boolean isValid(List<Answer> answers, ConstraintValidatorContext context) {
        LOGGER.debug("Controller: " + questionController);
        LOGGER.debug("Question Service: " + questionService);
        LOGGER.debug("Question Service EJB: " + questionServiceEJB);

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
