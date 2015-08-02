package de.beosign.quizzer.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Answer extends AbstractBaseEntity {
    private Question question;
    private String answerText;
    private boolean isCorrect;

    /**
     * JPA needs at least protected default constructor.
     */
    protected Answer() {
    }

    public Answer(Question question, String answerText, boolean isCorrect) {
        this.question = question;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    @ManyToOne
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

}
