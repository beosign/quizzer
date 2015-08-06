package de.beosign.quizzer.jpatest;

import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.jpatest.Order.OrderType;

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
    private EntityManager entityManager;

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

        entityManager.persist(order);

    }

}
