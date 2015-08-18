/**
 * 
 */
package de.beosign.quizzer.model;

/**
 * Defines an entity that has a numeric identifier
 * 
 * @author florian
 */
public interface BaseEntity<K> {
    /**
     * Returns the key
     * 
     * @return the key
     */
    K getKey();
}
