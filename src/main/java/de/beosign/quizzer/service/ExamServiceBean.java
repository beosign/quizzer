package de.beosign.quizzer.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import de.beosign.quizzer.logging.TraceInterceptor;
import de.beosign.quizzer.model.Exam;

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

    public ExamServiceBean() {
    }

    @Override
    protected Class<Exam> getGenericClass() {
        return Exam.class;
    }

}
