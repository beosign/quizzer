package de.beosign.quizzer.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import de.beosign.quizzer.logging.TraceInterceptor;
import de.beosign.quizzer.model.Course;

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
@Local(CourseService.class)
public class CourseServiceBean extends DefaultService<Course, String> implements CourseService {

    public CourseServiceBean() {
    }

    @Override
    protected Class<Course> getGenericClass() {
        return Course.class;
    }

}
