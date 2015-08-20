package de.beosign.quizzer.service;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import de.beosign.quizzer.logging.TraceInterceptor;
import de.beosign.quizzer.model.Course;
import de.beosign.quizzer.model.Exam;
import de.beosign.quizzer.model.ExamQuestion;
import de.beosign.quizzer.model.Question;

/**
 * Implementing {@link CourseService} and annotation {@link Local} are necessary because otherwise injection with @EJB will not work. Problem might be that
 * other interfaces (DefaultService) are
 * implemented as well.
 * 
 * @author Florian Dahlmanns
 *
 */
@Stateless
@Interceptors({ TraceInterceptor.class })
@Local(CourseService.class)
public class CourseServiceBean extends DefaultService<Course, String> implements CourseService {

    @EJB
    private ExamService examService;

    public CourseServiceBean() {
    }

    @Override
    protected Class<Course> getGenericClass() {
        return Course.class;
    }

    @Override
    public Exam createExam(Course course) {
        Exam exam = new Exam();
        exam.setCourse(course);

        for (Question q : course.getQuestions()) {
            ExamQuestion eq = new ExamQuestion();
            eq.setExam(exam);
            eq.setQuestion(q);
            exam.getExamQuestions().add(eq);
        }

        return examService.create(exam);
    }

}
