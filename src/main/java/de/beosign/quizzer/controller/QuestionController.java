package de.beosign.quizzer.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.model.Question;
import de.beosign.quizzer.service.QuestionService;

@Named
@SessionScoped
public class QuestionController implements Serializable {
    private static final long serialVersionUID = 2406204476528121383L;

    @Inject
    private Logger logger;

    @Inject
    private AddEditQuestionController addEditQuestionController;

    @EJB
    private QuestionService questionService;

    private List<Question> questions;

    public enum Pages {
        TESTADD("index"), ADD("addEditQuestion"), EDIT("addEditQuestion"), DELETE("questions?faces-redirect=true"), SELF("questions?faces-redirect=true"),
        BACK("index");

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
        logger.debug("questionService = {}", questionService.getClass().getName());

        questions = questionService.findAll();
    }

    public String doCreateTestQuestion() {
        addEditQuestionController.testAdd();
        return Pages.TESTADD.getOutcome();
    }

    public String doCreateQuestion() {
        logger.info("Create question");
        addEditQuestionController.add();

        return Pages.ADD.getOutcome();
    }

    public String doRefresh() {
        logger.info("Refreshing questions");

        questions = questionService.findAll();

        return Pages.SELF.getOutcome();
    }

    public String back() {
        logger.info("Back");

        return Pages.BACK.getOutcome();
    }

    public String doEditQuestion(Question question) {
        logger.info("Editing question {}", question);

        addEditQuestionController.edit(question);
        return Pages.EDIT.getOutcome();
    }

    public String doDeleteQuestion(Question question) {
        logger.info("Deleting question {}", question);

        questionService.delete(question.getId());

        return Pages.DELETE.getOutcome();
    }

    public QuestionService getQuestionService() {
        return questionService;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}
