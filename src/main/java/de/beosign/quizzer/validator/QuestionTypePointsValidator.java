package de.beosign.quizzer.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.beosign.quizzer.model.Question;

public class QuestionTypePointsValidator implements ConstraintValidator<QuestionTypePoints, Question> {
    private QuestionTypePoints questionTypePoints;

    @Override
    public void initialize(QuestionTypePoints questionTypePoints) {
        this.questionTypePoints = questionTypePoints;
    }

    @Override
    public boolean isValid(Question question, ConstraintValidatorContext context) {
        switch (question.getType()) {
        case MULTIPLE:
            return question.getPoints() >= question.getAnswers().size();
        default:
            return true;
        }
    }

}
