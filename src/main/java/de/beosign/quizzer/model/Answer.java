package de.beosign.quizzer.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Answer extends LongKeyBaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Question question;
    private String answerText;
    private boolean isCorrect;

    /**
     * JPA needs at least protected default constructor.
     */
    public Answer() {
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

    @NotNull
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
