/**
 * 
 */
package de.beosign.quizzer.model;

/**
 * Defines an entity that has a numeric identifier
 * 
 * @author florian
 */
public interface BaseEntity {
    /**
     * Returns the unique identifier as a long value
     * 
     * @return the id
     */
    Long getId();
}
