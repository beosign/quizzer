package de.beosign.quizzer.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.model.Course;
import de.beosign.quizzer.service.CourseServiceBean;
import de.beosign.quizzer.validator.ValidationUtil;

@Named
@SessionScoped
public class AddEditCourseController implements Serializable {
    private static final long serialVersionUID = -7052232834963685034L;

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

    @EJB
    private CourseServiceBean courseServiceBean;

    private Course course;

    private boolean isCourseEditMode;

    public enum Pages {
        OK("courses"), CANCEL("courses"), ADD_QUESTION, VALIDATION_FAILED;

        private final String outcome;

        Pages() {
            this("");
        }

        Pages(String outcome) {
            this.outcome = outcome;
        }

        public String getOutcome() {
            return outcome;
        }
    }

    public String add() {
        isCourseEditMode = false;
        course = new Course();

        return Pages.OK.outcome;
    }

    public String edit(Course course) {
        this.course = course;
        isCourseEditMode = true;

        return Pages.OK.outcome;
    }

    public String addQuestion() {
        return "";
    }

    public String editQuestion() {
        return "";
    }

    public String ok() {
        ValidationUtil.addValidationErrorsToFacesContext(course, facesContext);

        if (isCourseEditMode) {
            // courseServiceBean.update(course);
        } else {
            // courseServiceBean.create(course);
        }

        return Pages.OK.outcome;
    }

    public String cancel() {

        return Pages.CANCEL.outcome;
    }

}
