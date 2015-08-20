package de.beosign.quizzer.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@IdClass(ExamQuestionKey.class)
public class ExamQuestion {
    private Exam exam;
    private Question question;

    private List<Answer> givenAnswers = new ArrayList<>();

    public ExamQuestion() {
    }

    @Id
    @ManyToOne
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

    /**
     * The given answers. This is the owning side of the association as the entity {@link ExamQuestion} owns the foreign key for the answers. Therefore, the
     * column definition for the answer id must be used within the inverseJoinColumns association
     * 
     * @return the given answers.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ExamQuestionAnswers", joinColumns = { @JoinColumn(name = "exam_id"), @JoinColumn(name = "question_id") },
            inverseJoinColumns = { @JoinColumn(name = "answer_id") })
    public List<Answer> getGivenAnswers() {
        return givenAnswers;
    }

    public void setGivenAnswers(List<Answer> givenAnswers) {
        this.givenAnswers = givenAnswers;
    }

}
