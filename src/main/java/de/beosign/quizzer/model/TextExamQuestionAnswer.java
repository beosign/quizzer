package de.beosign.quizzer.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("T")
public class TextExamQuestionAnswer extends ExamQuestionAnswer {
    private static final long serialVersionUID = 1L;
    private String answerText;

    public TextExamQuestionAnswer() {
    }

    public TextExamQuestionAnswer(ExamQuestion examQuestion, Answer answer, String answerText) {
        super();
        setAnswer(answer);
        setExamQuestion(examQuestion);

        this.answerText = answerText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    @Transient
    public boolean isGivenAnswerCorrect() {

        if (getAnswer().getAnswerText().trim().equalsIgnoreCase(answerText)) {
            return true;
        } else {
            return false;
        }
    }

}
