package de.beosign.quizzer.service;

import de.beosign.quizzer.model.Course;
import de.beosign.quizzer.model.Exam;

public interface CourseService extends Service<Course, String> {
    Exam createExam(Course course);
}
