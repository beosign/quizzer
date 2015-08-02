package de.beosign.quizzer.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.event.Events.Added;
import de.beosign.quizzer.model.Question;
import de.beosign.quizzer.model.Question.Type;
import de.beosign.quizzer.service.QuestionService;
import de.beosign.quizzer.util.Resources;

@Named
@SessionScoped
public class AddEditQuestionController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

    @Inject
    @Added
    private Event<Question> questionAddedEvent;

    public enum Pages {
        OK("questions"), CANCEL("questions");

        private final String outcome;

        Pages(String outcome) {
            this.outcome = outcome;
        }

        public String getOutcome() {
            return outcome;
        }
    }

    @EJB
    private QuestionService questionService;

    private boolean isEditMode;
    private Question question;

    public boolean isEditMode() {
        return isEditMode;
    }

    public Question getQuestion() {
        return question;
    }

    void edit(Question question) {
        isEditMode = true;
        this.question = question;

    }

    void add() {
        isEditMode = false;
        this.question = new Question();
    }

    public String getTitle() {
        if (isEditMode) {
            return Resources.getResourceText(facesContext, "addEditQuestion.edit.title", question.getCode());
        } else {
            return Resources.getResourceText(facesContext, "addEditQuestion.add.title");
        }
    }

    void testAdd() {
        isEditMode = false;
        question = new Question("Code", "Text", Type.SINGLE, 3);
        questionService.create(question);

    }

    public String doOk() {
        logger.debug("OK clicked");

        if (isEditMode) {
            questionService.update(question);
        } else {
            questionAddedEvent.fire(question);

        }
        return Pages.OK.getOutcome();
    }

    public String doCancel() {
        logger.debug("Cancel clicked");
        return Pages.CANCEL.getOutcome();
    }
}
