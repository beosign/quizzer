/**
 * 
 */
package de.beosign.quizzer.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * All entities should extend this class as it already defines an identifier
 * field and a version field
 * 
 * @author florian
 */
@MappedSuperclass
public abstract class LongKeyBaseEntity implements BaseEntity<Long> {
    /**
     * Stores the current id
     */
    protected Long id;

    /**
     * Hibernate version field
     */
    protected Long version;

    @Transient
    protected DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Transient
    protected Logger logger = LogManager.getLogger();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Transient
    @Override
    public Long getKey() {
        return id;
    }

    /**
     * Used by hibernate
     * 
     * @param id
     *            the id to set
     */
    protected void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the version
     */
    @Version
    public Long getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(Long version) {
        this.version = version;
    }

}
