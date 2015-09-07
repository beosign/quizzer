package de.beosign.quizzer.jpatest;

import java.util.Calendar;
import java.util.Random;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.jpatest.Address.Type;
import de.beosign.quizzer.jpatest.Order.OrderType;
import de.beosign.quizzer.jpatest.Person.Sex;
import de.beosign.quizzer.service.QuestionService;
import de.beosign.quizzer.service.ejbtest.LocalBusinessBean;
import de.beosign.quizzer.service.ejbtest.LocalInterface;
import de.beosign.quizzer.service.ejbtest.LocalServiceBeanNoInterface;
import de.beosign.quizzer.service.ejbtest.LocalServiceBeanNoInterfaceView;

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

    @Inject
    private BeanManager beanManager;

    @Inject
    private LocalServiceBeanNoInterface localServiceBeanNoInterface;

    @Inject
    private LocalServiceBeanNoInterfaceView localServiceBeanNoInterfaceView;

    @Inject
    private LocalInterface localInterface;

    @Inject
    private LocalBusinessBean localBusinessBean;

    // @Inject
    // Injection not possible as NoBusiness is no Local or Remote interface. Be aware: Eclipse marks this as ambiguos problem, in reality, no bean is eligable
    // for injection
    // private NoBusiness noBusiness;

    @PostConstruct
    private void init() {
        logger.debug("!!!!!");
        localServiceBeanNoInterface.callMe();
        localServiceBeanNoInterfaceView.callMeNoBusiness();
        localInterface.localBusiness();
        localBusinessBean.callMeNoBusiness();
        localBusinessBean.localBusiness();
        noBusiness.callMeNoBusiness();

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

        listAllBeans();

    }

    private void listAllBeans() {

        Set<Bean<?>> beans = beanManager.getBeans(Object.class, new AnnotationLiteral<Any>() {
        });
        for (Bean<?> bean : beans) {
            logger.debug(bean.getBeanClass().getName());
        }

        logger.debug("Question Service beans:" + beanManager.getBeans(QuestionService.class));
    }
}
