package de.beosign.quizzer.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.model.Course;
import de.beosign.quizzer.service.CourseService;

@Named
@ViewScoped
public class CourseController implements Serializable {

    private static final long serialVersionUID = 2406204476528121383L;

    @Inject
    private Logger logger;

    // @Inject
    // private AddEditCourseController addEditCourseController;

    @EJB
    private CourseService courseService;

    private List<Course> courses;

    public enum Pages {
        ADD("addEditCourses"), EDIT("addEditCourses"), DELETE("courses?faces-redirect=true"), SELF("courses?faces-redirect=true"), BACK("index");

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
        courses = courseService.findAll();
    }

    public String doCreateCourse() {
        logger.info("Create courseService");

        // addEditCourseController.add();
        return Pages.ADD.getOutcome();
    }

    public String doRefresh() {
        logger.info("Refreshing courses");

        return Pages.SELF.getOutcome();
    }

    public String back() {
        return Pages.BACK.getOutcome();
    }

    public String doEditCourse(Course course) {
        logger.info("Editing {}", course);

        // addEditCourseController.edit(course);
        return Pages.EDIT.getOutcome();
    }

    public String doTakeExam(Course course) {
        logger.info("Taking exam for {}", course);

        return TakeExamController.Pages.START.getOutcome();
    }

    public String doDeleteCourse(Course course) {
        logger.info("Deleting {}", course);

        courseService.delete(course.getKey());

        return Pages.DELETE.getOutcome();
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}
