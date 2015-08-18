package de.beosign.quizzer.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Min;

@Entity
public class Course implements BaseEntity<String> {
    private String name;
    private Long version;
    private List<Question> questions = new ArrayList<>();
    private int minScoreToPass = 50;
    private Duration allowedDuration = Duration.ofMinutes(60);
    private List<Exam> exams = new ArrayList<>();

    public Course() {
    }

    @ManyToMany
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Min(1)
    public int getMinScoreToPass() {
        return minScoreToPass;
    }

    public void setMinScoreToPass(int minScoreToPass) {
        this.minScoreToPass = minScoreToPass;
    }

    public Duration getAllowedDuration() {
        return allowedDuration;
    }

    public void setAllowedDuration(Duration allowedDuration) {
        this.allowedDuration = allowedDuration;
    }

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @OneToMany(mappedBy = "course")
    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    @Transient
    @Override
    public String getKey() {
        return name;
    }

}
