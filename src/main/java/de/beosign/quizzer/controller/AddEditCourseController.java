package de.beosign.quizzer.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;

import de.beosign.quizzer.model.Course;
import de.beosign.quizzer.model.Question;
import de.beosign.quizzer.service.CourseService;
import de.beosign.quizzer.service.QuestionService;
import de.beosign.quizzer.util.FacesUtil;
import de.beosign.quizzer.validator.ValidationUtil;

@Named
@ViewScoped
public class AddEditCourseController implements Serializable {
    private static final long serialVersionUID = -7052232834963685034L;

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

    @EJB
    private CourseService courseService;

    @EJB
    private QuestionService questionService;

    private Course course;

    private boolean isCourseEditMode;

    private Question selectedQuestion;

    private DualListModel<Question> questionDualListModel;

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

    @PostConstruct
    private void init() {
        logger.debug("Setting up dual list");
        List<Question> selected = new ArrayList<>();
        if (course != null) {
            selected = course.getQuestions();
        }
        List<Question> available = questionService.findAll();

        available.removeAll(selected);
        questionDualListModel = new DualListModel<>(available, selected);

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

    public String getTitle() {
        if (isCourseEditMode) {
            return FacesUtil.getResourceText(facesContext, "addEditCourse.edit.title", course.getName());
        } else {
            return FacesUtil.getResourceText(facesContext, "addEditCourse.add.title");
        }
    }

    public String ok() {
        ValidationUtil.addValidationErrorsToFacesContext(course, facesContext);

        if (isCourseEditMode) {
            courseService.update(course);
        } else {
            courseService.create(course);
        }

        return Pages.OK.outcome;
    }

    public String cancel() {

        return Pages.CANCEL.outcome;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public DualListModel<Question> getQuestionDualListModel() {
        return questionDualListModel;
    }

    public void onQuestionSelected(SelectEvent event) {
        logger.debug(event.getObject().getClass().getName() + ": " + event.getObject());
        selectedQuestion = (Question) event.getObject();
    }

    public void valueChangeMethod(ValueChangeEvent e) {
        logger.debug(e.getNewValue().getClass().getName());
    }

    public void setQuestionDualListModel(DualListModel<Question> questionDualListModel) {
        this.questionDualListModel = questionDualListModel;
    }

    public QuestionService getQuestionService() {
        return questionService;
    }

    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public Question getSelectedQuestion() {
        return selectedQuestion;
    }

    public void setSelectedQuestion(Question selectedQuestion) {
        this.selectedQuestion = selectedQuestion;
    }

}
