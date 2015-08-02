/**
 * 
 */
package de.beosign.quizzer.service;

import java.util.List;
import java.util.Optional;

import de.beosign.quizzer.model.BaseEntity;

/**
 * Base interface for a Service.
 * 
 * @param <T> entity type
 * @author florian
 */
public interface Service<T extends BaseEntity> {
    /**
     * Returns the entity by id.
     * 
     * @param id id
     * @return the entity by id.
     */
    Optional<T> find(long id);

    /**
     * Finds all entities.
     * 
     * @return All entities.
     */
    List<T> findAll();

    /**
     * Creates / stores the given entity.
     * 
     * @param t entity to store
     * @return stored entity
     */
    T create(T t);

    /**
     * Updates given entity.
     * 
     * @param t entity
     * @return updated entity
     */
    T update(T t);

    /**
     * Deleted the given entity and returns it.
     * 
     * @param id id of entity
     * @return deleted entity
     */
    Optional<T> delete(long id);

}
