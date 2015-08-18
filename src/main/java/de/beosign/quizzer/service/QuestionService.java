package de.beosign.quizzer.service;

import java.util.List;

import de.beosign.quizzer.model.Question;

public interface QuestionService extends Service<Question, Long> {

    /**
     * Method must be overridden to make CDI events work.
     */
    @Override
    Question create(Question t);

    @Override
    List<Question> findAll();
}
