package de.beosign.quizzer.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorColumn(name = "D", length = 1)
public abstract class ExamQuestionAnswer extends LongKeyBaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Answer answer;
    private ExamQuestion examQuestion;

    @OneToOne(fetch = FetchType.EAGER)
    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public ExamQuestion getExamQuestion() {
        return examQuestion;
    }

    public void setExamQuestion(ExamQuestion examQuestion) {
        this.examQuestion = examQuestion;
    }

    @Transient
    public abstract boolean isGivenAnswerCorrect();

}
