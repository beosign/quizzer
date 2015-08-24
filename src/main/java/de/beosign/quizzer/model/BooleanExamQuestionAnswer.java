package de.beosign.quizzer.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("B")
public class BooleanExamQuestionAnswer extends ExamQuestionAnswer {
    private boolean isCorrect;

    public BooleanExamQuestionAnswer() {
    }

    public BooleanExamQuestionAnswer(ExamQuestion examQuestion, Answer answer, boolean isCorrect) {
        super();
        setAnswer(answer);
        setExamQuestion(examQuestion);

        this.isCorrect = isCorrect;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "BooleanExamQuestionAnswer [isCorrect=" + isCorrect + "]";
    }

    @Transient
    @Override
    public boolean isGivenAnswerCorrect() {
        return getAnswer().isCorrect() == isCorrect;
    }

}
