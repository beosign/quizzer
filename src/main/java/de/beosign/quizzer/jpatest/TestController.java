package de.beosign.quizzer.jpatest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.jpatest.Person.Sex;

@ApplicationScoped
@Named
public class TestController {

    @Inject
    private EntityManager em;

    @Inject
    private Logger logger;

    public String testQueries() {
        TypedQuery<Person> allPersonsQuery = em.createQuery("select p from Person p", Person.class);

        allPersonsQuery.getResultList().forEach(p -> {
            logger.debug("All Persons: {}", p);
        });

        TypedQuery<Person> malePersonsQuery = em.createQuery("select p from Person p where p.sex = " + Sex.class.getName() + "." + Sex.MALE, Person.class);
        malePersonsQuery.getResultList().forEach(p -> {
            logger.debug("Male Persons: {}", p);
        });

        return "";
    }
}
