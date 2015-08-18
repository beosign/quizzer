package de.beosign.quizzer.service;

import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.event.Events.Added;
import de.beosign.quizzer.logging.Log;
import de.beosign.quizzer.logging.TraceInterceptor;
import de.beosign.quizzer.model.Question;

@Stateless
@Interceptors({ TraceInterceptor.class })
@Local(QuestionService.class)
public class QuestionServiceBean extends DefaultService<Question, Long> implements QuestionService {
    private static final AtomicLong INSTANCE_COUNTER = new AtomicLong(0);

    @Inject
    private Logger logger;

    @PostConstruct
    private void init() {
        logger.debug(getClass().getSimpleName() + " instances: {}", INSTANCE_COUNTER.incrementAndGet());
    }

    @Override
    public Question create(@Observes @Added Question question) {
        return super.create(question);
    }

    public static void onQuestionAdded(@Observes @Added Question question) {
        Log.logger().debug("[EVENT {}]: {}", Added.class.getSimpleName(), question);
    }

    @Override
    protected Class<Question> getGenericClass() {
        return Question.class;
    }

}
