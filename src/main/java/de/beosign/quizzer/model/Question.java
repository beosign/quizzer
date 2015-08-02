package de.beosign.quizzer.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Question extends AbstractBaseEntity {
    public enum Type {
        SINGLE, MULTIPLE, TEXT
    }

    private String code;
    private String questionText;
    private Type type;
    private int points;
    private List<Answer> answers;

    public Question() {
    }

    public Question(String code, String questionText, Type type, int points) {
        super();
        this.code = code;
        this.questionText = questionText;
        this.type = type;
        this.points = points;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Question)) {
            return false;
        }
        Question other = (Question) obj;
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Question [code=" + code + ", questionText=" + questionText + ", type=" + type + ", points=" + points + "]";
    }

    @NotNull
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotNull
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @NotNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Min(1)
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    public List<Answer> getAnswers() {
        return answers;
    }

    void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

}
