package de.beosign.quizzer.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.apache.logging.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
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

    private String courseName;

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

    // @PostConstruct
    // private void init() {
    // logger.debug("Setting up members");
    // course = new Course();
    // questionDualListModel = new DualListModel<>(new ArrayList<>(), new ArrayList<>());
    // isCourseEditMode = false;
    //
    // }

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

    public void onQuestionTransferred(TransferEvent event) {
        logger.debug(event.getSource() + ", " + event.getItems() + ", " + event.getComponent().getId() + ", added = " + event.isAdd() + ", removed = "
                + event.isRemove());

        if (event.isAdd()) {
            course.getQuestions().addAll((Collection<? extends Question>) event.getItems());
        } else {
            course.getQuestions().removeAll(event.getItems());
        }
        logger.debug("Current question count: " + course.getQuestions().size());

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

    /**
     * Getter for view parameter
     * 
     * @return course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Setter for view parameter
     * 
     * @param courseName course name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * This method is called when a view parameter <tt>courseName</tt> is present. This method is the target of a viewAction facet in the jsf corresponding to
     * this controller. The logic of the init method was moved here, as the parameter will be not available during the init method, but only later when this
     * method is called. You <b>must</b> call the JSF view using a GET with the request param, otherwise the members will not be initialized correctly!
     * 
     * Needs to be transactional because otherwise the removeAll method cannot access the questions (LazyInitException).
     */
    @Transactional
    public void initAfterViewParams() {
        if (courseName != null && !courseName.isEmpty()) {
            logger.debug("Editing course {}", courseName);
            course = courseService.find(courseName).orElse(null);
        } else {
            logger.debug("Adding course {}", courseName);
        }

        List<Question> selected = new ArrayList<>();
        if (course != null) {
            isCourseEditMode = true;
            selected = course.getQuestions();
        } else {
            isCourseEditMode = false;
            course = new Course();
        }

        logger.debug("Setting up dual list");

        // Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        // String n = params.get("courseName");

        List<Question> available = questionService.findAll();

        available.removeAll(selected);
        questionDualListModel = new DualListModel<>(available, selected);
    }

}
