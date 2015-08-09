package de.beosign.quizzer.jpatest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.jpatest.Person.Sex;

@ApplicationScoped
@Named
public class TestController {

    @Inject
    private EntityManager em;

    @Inject
    private Logger logger;

    @Transactional
    public String testQueries() {
        TypedQuery<Person> allPersonsQuery = em.createQuery("select p from Person p", Person.class);

        allPersonsQuery.getResultList().forEach(p -> {
            logger.debug("All Persons: {}", p);
        });

        TypedQuery<Person> malePersonsQuery = em.createQuery("select p from Person p where p.sex = " + Sex.class.getName() + "." + Sex.MALE, Person.class);
        malePersonsQuery.getResultList().forEach(p -> {
            logger.debug("Male Persons: {}", p);
        });

        TypedQuery<Person> personsInExpensiveClubQuery = em.createQuery("select p from Person p, IN (p.clubs) c where c.fee >= 100", Person.class);
        personsInExpensiveClubQuery.getResultList().forEach(p -> {
            logger.debug("Persons in expensive Clubs: {}", p);
        });

        Long minClubs = 1L;
        TypedQuery<Person> personsInMoreThanXClubsQuery = em.createQuery("select p from Person p, IN (p.clubs) c group by p having count(c) >= ?1",
                Person.class);
        personsInMoreThanXClubsQuery.setParameter(1, minClubs);
        personsInMoreThanXClubsQuery.getResultList().forEach(p -> {
            logger.debug("Persons in at least {} Clubs: {}", minClubs, p);
        });

        Long minMembers = 1L;
        TypedQuery<Club> clubsWithAtLeastXMembers = em.createQuery("select c from Club c, IN (c.members) m group by c having count(m) >= ?1", Club.class);
        clubsWithAtLeastXMembers.setParameter(1, minMembers);
        clubsWithAtLeastXMembers.getResultList().forEach(c -> {
            logger.debug("Clubs with at least {} members : {}", minClubs, c);
        });

        return "";
    }

    @Transactional
    public String testQueriesCriteria() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> allPersonsCq = cb.createQuery(Person.class);
        Root<Person> personRoot = allPersonsCq.from(Person.class);
        allPersonsCq.select(personRoot);
        TypedQuery<Person> allPersonsQuery = em.createQuery(allPersonsCq);

        allPersonsQuery.getResultList().forEach(p -> {
            logger.debug("All persons: {} " + p);
        });

        CriteriaQuery<Person> malePersonsCq = em.getCriteriaBuilder().createQuery(Person.class);
        personRoot = malePersonsCq.from(Person.class);
        malePersonsCq.select(personRoot);
        malePersonsCq.where(cb.equal(personRoot.get(Person_.sex), Sex.MALE));
        TypedQuery<Person> malePersonsQuery = em.createQuery(malePersonsCq);

        malePersonsQuery.getResultList().forEach(p -> {
            logger.debug("Male persons: {} " + p);
        });

        // --- Persons in expensive clubs --- //
        CriteriaQuery<Person> personsInExpensiveClubCq = em.getCriteriaBuilder().createQuery(Person.class);
        personRoot = personsInExpensiveClubCq.from(Person.class);
        ListJoin<Person, Club> joinedClubs = personRoot.join(Person_.clubs);
        personsInExpensiveClubCq.select(personRoot);
        personsInExpensiveClubCq.where(cb.ge(joinedClubs.get(Club_.fee), 100));
        TypedQuery<Person> personsInExpensiveClubQuery = em.createQuery(personsInExpensiveClubCq);

        personsInExpensiveClubQuery.getResultList().forEach(p -> {
            logger.debug("Persons in expensive clubs: {} " + p);
        });

        // --- Persons in at least X club --- //
        int minClubs = 1;
        CriteriaQuery<Person> personsInAtLeastXClubsCq = em.getCriteriaBuilder().createQuery(Person.class);
        personRoot = personsInAtLeastXClubsCq.from(Person.class);
        joinedClubs = personRoot.join(Person_.clubs);
        personsInAtLeastXClubsCq.select(personRoot);
        personsInAtLeastXClubsCq.groupBy(personRoot);
        personsInAtLeastXClubsCq.having(cb.ge(cb.count(joinedClubs), minClubs));

        TypedQuery<Person> personsInAtLeastXClubsQuery = em.createQuery(personsInAtLeastXClubsCq);

        personsInAtLeastXClubsQuery.getResultList().forEach(p -> {
            logger.debug("Persons in at least {} clubs {} ", minClubs, p);
        });

        return "";
    }
}
