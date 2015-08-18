package de.beosign.quizzer.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.model.Course;
import de.beosign.quizzer.service.CourseService;

@Named
@SessionScoped
public class CourseController implements Serializable {

    private static final long serialVersionUID = 2406204476528121383L;

    @Inject
    private Logger logger;

    @Inject
    private AddEditCourseController addEditCourseController;

    @EJB
    private CourseService courseService;

    public enum Pages {
        ADD("addEditCourses"), EDIT("addEditCourses"), DELETE("courses?faces-redirect=true"), SELF("courses?faces-redirect=true");

        private final String outcome;

        Pages(String outcome) {
            this.outcome = outcome;
        }

        public String getOutcome() {
            return outcome;
        }
    }

    @PostConstruct
    private void init() {
        logger.debug("courseService = {}", courseService.getClass().getName());
    }

    public String doCreateCourse() {
        logger.info("Create courseService");
        addEditCourseController.add();
        return Pages.ADD.getOutcome();
    }

    public String doRefresh() {
        logger.info("Refreshing courses");

        return Pages.SELF.getOutcome();
    }

    public String doEditCourse(Course course) {
        logger.info("Editing {}", course);

        addEditCourseController.edit(course);
        return Pages.EDIT.getOutcome();
    }

    public String doDeleteCourse(Course course) {
        logger.info("Deleting {}", course);

        courseService.delete(course.getKey());

        return Pages.DELETE.getOutcome();
    }

    public CourseService getCourseService() {
        return courseService;
    }

}
