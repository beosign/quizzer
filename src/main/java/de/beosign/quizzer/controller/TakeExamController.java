package de.beosign.quizzer.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.model.Course;
import de.beosign.quizzer.model.Exam;
import de.beosign.quizzer.service.CourseService;
import de.beosign.quizzer.service.ExamService;

@Named
@ConversationScoped
public class TakeExamController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

    @Inject
    private Conversation conversation;

    @EJB
    private CourseService courseService;

    @EJB
    private ExamService examService;

    private Course course;

    private Exam exam;

    private String courseName;

    public enum Pages {
        START("exam"), CANCEL("index"), SELF("exam?faces-redirect=true"), BACK("index"), END("end-exam");

        private final String outcome;

        Pages(String outcome) {
            this.outcome = outcome;
        }

        public String getOutcome() {
            return outcome;
        }
    }

    public void initConversation() {
        if (!facesContext.isPostback() && conversation.isTransient()) {
            conversation.begin();
            logger.debug("Conversation has started");
        }
    }

    public String endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        return Pages.END.getOutcome();
    }

    public String startExam() {

        return Pages.START.getOutcome();
    }

    @Transactional
    public void initAfterViewParams() {
        if (courseName != null) {
            logger.debug("Taking exam for course {}", courseName);

            course = courseService.find(courseName).orElseThrow(() -> new IllegalArgumentException("No course found with name " + courseName));
            exam = courseService.createExam(course);

            logger.info("Created exam {} from course {}", exam, course);

        } else {
            throw new IllegalArgumentException("No examId found in GET parameter");
        }
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

}
