package de.beosign.quizzer.jpatest;

import java.util.Calendar;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.jpatest.Address.Type;
import de.beosign.quizzer.jpatest.Order.OrderType;
import de.beosign.quizzer.jpatest.Person.Sex;

/**
 * Used during startup to insert some test data.
 * 
 * @author Florian Dahlmanns
 *
 */
// Because we want EJB functionality (TX management), we use @Singleton, and not @ApplicationScoped
// See http://germanescobar.net/2010/04/4-areas-of-possible-confusion-in-jee6.html
@Singleton
@Startup
public class TestOrderStartupBean {
    @Inject
    private EntityManager em;

    @Inject
    private Logger logger;

    @PostConstruct
    private void init() {
        logger.debug("!!!!!");
        Order order = new Order();

        LineItem li = new LineItem(order, 1);
        LineItem li2 = new LineItem(order, 2);

        order.getLineItems().add(li);
        order.getLineItems().add(li2);

        if (new Random().nextBoolean()) {
            if (new Random().nextBoolean()) {
                order.setOrderType(OrderType.VIP);
            } else {
                order.setOrderType(OrderType.PRIME);
            }
        }

        em.persist(order);

        // --- test entities --- //
        Query q = em.createQuery("delete from Club c");
        int deleted = q.executeUpdate();
        logger.debug("Deleted {} clubs", deleted);

        q = em.createQuery("delete from Address a");
        deleted = q.executeUpdate();
        logger.debug("Deleted {} addresses", deleted);

        q = em.createQuery("delete from Person p");
        deleted = q.executeUpdate();
        logger.debug("Deleted {} persons", deleted);

        Calendar cal = Calendar.getInstance();
        cal.set(1980, 12, 25);

        Person homer = new Person();
        homer.setDateOfBirth(cal.getTime());
        homer.setSex(Sex.MALE);
        homer.setFirstName("Homer");
        homer.setLastName("Simpson");

        cal.set(1981, 7, 3);
        Person marge = new Person();
        marge.setDateOfBirth(cal.getTime());
        marge.setSex(Sex.FEMALE);
        marge.setFirstName("Marge");
        marge.setLastName("Simpson");

        Address simpsonHomeAddress = new Address();
        simpsonHomeAddress.setCountry("USA");
        simpsonHomeAddress.setPerson(homer);
        simpsonHomeAddress.setPerson(marge);
        simpsonHomeAddress.setStreet("Evergreen Terrace 741");
        simpsonHomeAddress.setType(Type.HOME);
        simpsonHomeAddress.setZipCode("1234");
        homer.getAddresses().add(simpsonHomeAddress);
        marge.getAddresses().add(simpsonHomeAddress);

        Address plantAddress = new Address();
        plantAddress.setCountry("USA");
        plantAddress.setPerson(homer);
        plantAddress.setStreet("Plant Street 1");
        plantAddress.setType(Type.WORK);
        plantAddress.setZipCode("1234");
        homer.getAddresses().add(plantAddress);

        Club springshieldClub = new Club();
        springshieldClub.setConstitutionYear(1990);
        springshieldClub.setName("Springshield");
        springshieldClub.setFee(145);
        springshieldClub.getMembers().add(homer);
        homer.getClubs().add(springshieldClub);

        Club womenClub = new Club();
        womenClub.setConstitutionYear(1995);
        womenClub.setName("Women Club");
        womenClub.setFee(85);
        womenClub.getMembers().add(marge);
        marge.getClubs().add(womenClub);

        em.persist(homer);
        em.persist(marge);

    }

}
