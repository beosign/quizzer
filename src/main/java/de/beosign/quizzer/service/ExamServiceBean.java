package de.beosign.quizzer.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.logging.TraceInterceptor;
import de.beosign.quizzer.model.BooleanExamQuestionAnswer;
import de.beosign.quizzer.model.Exam;
import de.beosign.quizzer.model.ExamQuestion;
import de.beosign.quizzer.model.ExamQuestionAnswer;
import de.beosign.quizzer.model.TextExamQuestionAnswer;

/**
 * Implementing {@link CourseService} and annotation {@link Local} are necessary because otherwise injection with @EJB will not work. Problem might be that
 * other interfaces (DefaultService) are
 * implemented as well.
 * 
 * @author Florian Dahlmanns
 *
 */
@Stateless
@Interceptors({ TraceInterceptor.class })
@Local(ExamService.class)
public class ExamServiceBean extends DefaultService<Exam, Long> implements ExamService {

    @Inject
    private Logger logger;

    public ExamServiceBean() {
    }

    @Override
    protected Class<Exam> getGenericClass() {
        return Exam.class;
    }

    @Override
    public ExamQuestion updateExamQuestion(ExamQuestion examQuestion) {
        ExamQuestion found = getEntityManager().find(ExamQuestion.class, examQuestion.getKey());

        logger.debug("Found ExamQuestion: {}", found);

        examQuestion = getEntityManager().merge(examQuestion);
        return examQuestion;
    }

    @Override
    public ExamQuestionAnswer updateExamQuestionAnswer(ExamQuestionAnswer examQuestionAnswer) {
        ExamQuestionAnswer updatedAnswer;

        if (examQuestionAnswer instanceof BooleanExamQuestionAnswer) {
            BooleanExamQuestionAnswer found = getEntityManager().find(BooleanExamQuestionAnswer.class, examQuestionAnswer.getId());
            updatedAnswer = found;
            logger.debug("Found ExamQuestionAnswer: {}", found);
            found.setCorrect(((BooleanExamQuestionAnswer) examQuestionAnswer).isCorrect());
        } else {
            TextExamQuestionAnswer found = getEntityManager().find(TextExamQuestionAnswer.class, examQuestionAnswer.getId());
            updatedAnswer = found;
            logger.debug("Found ExamQuestionAnswer: {}", found);
            found.setAnswerText(((TextExamQuestionAnswer) examQuestionAnswer).getAnswerText());

        }

        return updatedAnswer;
    }

}
