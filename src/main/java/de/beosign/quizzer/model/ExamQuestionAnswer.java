package de.beosign.quizzer.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorColumn(name = "D", length = 1)
public abstract class ExamQuestionAnswer extends LongKeyBaseEntity {
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

}
