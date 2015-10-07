package de.beosign.quizzer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
@IdClass(ExamQuestionKey.class)
public class ExamQuestion implements Serializable {
    private static final long serialVersionUID = 1;

    private Exam exam;
    private Question question;

    private List<ExamQuestionAnswer> givenAnswers = new ArrayList<>();

    public ExamQuestion() {
    }

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    @Id
    @ManyToOne
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "examQuestion", fetch = FetchType.EAGER)
    public List<ExamQuestionAnswer> getGivenAnswers() {
        return givenAnswers;
    }

    public void setGivenAnswers(List<ExamQuestionAnswer> givenAnswers) {
        this.givenAnswers = givenAnswers;
    }

    @Transient
    public List<BooleanExamQuestionAnswer> getBooleanGivenAnswers() {
        List<BooleanExamQuestionAnswer> booleanExamQuestionAnswers = new ArrayList<>();

        for (ExamQuestionAnswer a : givenAnswers) {
            if (a instanceof BooleanExamQuestionAnswer) {
                booleanExamQuestionAnswers.add((BooleanExamQuestionAnswer) a);
            }
        }

        return booleanExamQuestionAnswers;
    }

    @Transient
    public ExamQuestionKey getKey() {
        return new ExamQuestionKey(exam.getId(), question.getId());
    }
}
