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
import de.beosign.quizzer.model.Answer;
import de.beosign.quizzer.model.Question;
import de.beosign.quizzer.model.Question.Type;
import de.beosign.quizzer.service.QuestionService;
import de.beosign.quizzer.util.FacesUtil;
import de.beosign.quizzer.validator.ValidationUtil;

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

    private boolean isAnswerEditMode = false;

    public enum Pages {
        OK("questions"), CANCEL("questions"), ADD_ANSWER, VALIDATION_FAILED;

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

    @EJB
    private QuestionService questionService;

    private boolean isEditMode;
    private Question question;

    // private Answer answer;

    public boolean isEditMode() {
        return isEditMode;
    }

    public Question getQuestion() {
        return question;
    }

    public String addNewAnswer() {
        Answer answer = new Answer();
        answer.setQuestion(question);
        question.getAnswers().add(answer);
        return Pages.ADD_ANSWER.outcome;
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
            return FacesUtil.getResourceText(facesContext, "addEditQuestion.edit.title", question.getCode());
        } else {
            return FacesUtil.getResourceText(facesContext, "addEditQuestion.add.title");
        }
    }

    public String getAnswerButtonTitle() {
        if (isAnswerEditMode) {
            return FacesUtil.getResourceText(facesContext, "addEditQuestion.answer.button.edit");
        } else {
            return FacesUtil.getResourceText(facesContext, "addEditQuestion.answer.button.add");
        }
    }

    void testAdd() {
        isEditMode = false;
        question = new Question("Code", "Text", Type.SINGLE, 3);
        questionService.create(question);

    }

    public String doOk() {
        logger.debug("OK clicked");

        /*
         * Must manually validate whole object as JSF can only validate fields
         * that occur on the form.
         * See http://stackoverflow.com/questions/16210972/force-jsf-to-bean-validate-all-fields-of-my-bean-not-only-those-bound-to-an-inp
         */

        if (ValidationUtil.addValidationErrorsToFacesContext(question, facesContext).size() > 0) {
            return Pages.VALIDATION_FAILED.outcome;
        }

        if (isEditMode) {
            questionService.update(question);
        } else {
            questionAddedEvent.fire(question);

        }
        return Pages.OK.getOutcome();
    }

    public String doDeleteAnswer(Answer answer) {
        logger.debug("Delete answer clicked");

        question.getAnswers().remove(answer);

        return Pages.ADD_ANSWER.getOutcome();
    }

    public String doCancel() {
        logger.debug("Cancel clicked");
        return Pages.CANCEL.getOutcome();
    }

}
