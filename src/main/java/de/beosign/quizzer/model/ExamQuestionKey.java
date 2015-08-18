package de.beosign.quizzer.model;

import java.io.Serializable;

public class ExamQuestionKey implements Serializable {
    private static final long serialVersionUID = 1812145319571292138L;

    // Name this property exactly as the one in ExamQuestion!
    // This must be the primitive type of the id field, not the java entity!
    private Long exam;

    // Name this property exactly as the one in ExamQuestion!
    // This must be the primitive type of the id field, not the java entity!
    private Long question;

    protected ExamQuestionKey() {
    }

    public ExamQuestionKey(Long examId, Long questionId) {
        this.exam = examId;
        this.question = questionId;
    }

    public Long getExam() {
        return exam;
    }

    public void setExam(Long exam) {
        this.exam = exam;
    }

    public Long getQuestion() {
        return question;
    }

    public void setQuestion(Long question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "ExamQuestionKey [exam=" + exam + ", question=" + question + "]";
    }

}
