package de.beosign.quizzer.jpa;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceProviderResolverHolder;

import org.junit.BeforeClass;
import org.junit.Test;

public class JpaTestBase {
    protected static EntityManagerFactory emf;
    protected static EntityManager em;

    @Test
    public void test() {

    }

    @BeforeClass
    public static void setupJpa() {
        // Get the entity manager for the tests.

        HashMap<String, Object> map = new HashMap<>();
        // <properties>
        // <property name="hibernate.show_sql" value="true" />
        // <property name="javax.persistence.jdbc.driver" value="org.apache.derby.jdbc.EmbeddedDriver" />
        // <property name="javax.persistence.jdbc.url" value="jdbc:derby:memory:QuizzerDB;create=true" />
        // <property name="javax.persistence.jdbc.user" value="" />
        // <property name="javax.persistence.jdbc.password" value="" />
        //
        // <property name="hibernate.dialect" value="org.hibernate.dialect.DerbyTenFiveDialect"/>
        // <property name="hibernate.hbm2ddl.auto" value="update"/>
        // <property name="hibernate.id.new_generator_mappings" value="true"/>
        // </properties>

        map.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
        map.put("javax.persistence.jdbc.url", "jdbc:derby:memory:QuizzerDB;create=true");
        map.put("javax.persistence.jdbc.user", "");
        map.put("javax.persistence.jdbc.password", "");
        map.put("hibernate.dialect", "org.hibernate.dialect.DerbyTenFiveDialect");
        map.put("hibernate.hbm2ddl.auto", "update");
        map.put("hibernate.id.new_generator_mappings", "true");

        System.out.println(PersistenceProviderResolverHolder.getPersistenceProviderResolver().getPersistenceProviders().size());

        emf = Persistence.createEntityManagerFactory("quizzer");
        em = emf.createEntityManager();
        EntityTransaction trx = null;
        try {
            // Get a new transaction
            trx = em.getTransaction();

            // Start the transaction
            trx.begin();
            // Persist the object in the DB
            // Commit and end the transaction
            trx.commit();
        } catch (RuntimeException e) {
            if (trx != null && trx.isActive()) {
                trx.rollback();
            }
            throw e;
        } finally {
            // Close the manager
            em.close();
            emf.close();
        }

    }
}
