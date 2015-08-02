/**
 * 
 */
package de.beosign.quizzer.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.model.BaseEntity;
import de.beosign.quizzer.util.PersistenceHelper;

/**
 * The Class DefaultService.
 *
 * @author florian
 * @param <T> the generic type
 */
@Dependent
public abstract class DefaultService<T extends BaseEntity> implements Service<T> {

    @Inject
    protected Logger logger;

    @PersistenceContext(unitName = PersistenceHelper.PERSISTENCE_UNIT)
    private EntityManager em;

    /**
     * Gets the generic class.
     *
     * @return the generic class
     */
    protected abstract Class<T> getGenericClass();

    @Override
    public T create(T t) {
        logger.info("Creating object {}", t);
        em.persist(t);

        try {
            em.persist(t);
            em.flush();
            return t;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public T update(T t) {
        logger.info("Updating object {}", t);
        em.find(t.getClass(), t.getId());

        try {
            t = em.merge(t);
            em.flush();
            return t;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<T> find(long id) {
        logger.info("Finding object of type {} with id {}", getGenericClass().getName(), id);
        return Optional.ofNullable(em.find(getGenericClass(), id));
    }

    @Override
    public List<T> findAll() {
        logger.info("Finding all objects of type {}", getGenericClass().getName());
        TypedQuery<T> q = em.createQuery("from " + getGenericClass().getSimpleName() + "  t", getGenericClass());

        logger.debug("Found {} objects of type {}", q.getResultList().size(), getGenericClass().getSimpleName());
        return q.getResultList();
    }

    @Override
    public Optional<T> delete(long id) {
        logger.info("Deleting object of type {} with id {}", getGenericClass().getName(), id);
        Optional<T> m = find(id);
        if (m.isPresent()) {
            try {
                em.remove(m.get());
                em.flush();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw e;
            }

        }
        return m;

    }

    /**
     * @return the EntityManager
     */
    public EntityManager getEntityManager() {
        return em;
    }
}
