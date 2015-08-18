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
 * @param <K> key type
 * @author florian
 */
public interface Service<T extends BaseEntity<K>, K> {
    /**
     * Returns the entity by key.
     * 
     * @param key key
     * @return the entity by key.
     */
    Optional<T> find(K key);

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
     * @param key key of entity
     * @return deleted entity
     */
    Optional<T> delete(K key);

}
