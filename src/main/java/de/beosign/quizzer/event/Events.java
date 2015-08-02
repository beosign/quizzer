package de.beosign.quizzer.event;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

public class Events {

    @Documented
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Added {

    }

    @Documented
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Deleted {

    }

    @Documented
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Updated {

    }

}
