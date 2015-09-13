package de.beosign.quizzer.validator;

import static org.junit.Assert.*;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//@RunWith(CdiRunner.class)
public class ValidationUtilTest {

    /**
     * Unit under test.
     * private ValidationUtil validationUtil;
     */
    @Inject
    private ValidationUtil validationUtil;

    private FacesContext facesContext;

    @Before
    public void setup() {
        facesContext = null;
        Assert.assertNotNull(validationUtil);

    }

    @Test
    public void addValidationErrorsToFacesContextNoError() {
        ClassToBeValidated c = new ClassToBeValidated();
        c.minFive = 5;
        c.notNullString = "Hi";

        List<FacesMessage> messages = validationUtil.addValidationErrorsToFacesContext(c);
        assertEquals("No messages expected", 0, messages.size());
    }

    @Test
    public void addValidationErrorsToFacesContextSingleError() {
        ClassToBeValidated c = new ClassToBeValidated();
        c.minFive = 2;
        c.notNullString = "Hi";

        List<FacesMessage> messages = validationUtil.addValidationErrorsToFacesContext(c);
        assertEquals("Exactly one message expected", 1, messages.size());

    }

    @Test
    public void addValidationErrorsToFacesContextMultipleErrors() {
        ClassToBeValidated c = new ClassToBeValidated();
        c.minFive = 2;
        c.notNullString = null;

        List<FacesMessage> messages = validationUtil.addValidationErrorsToFacesContext(c);
        assertEquals("Two messages expected", 2, messages.size());

    }

    private static class ClassToBeValidated {
        @NotNull
        private String notNullString;

        @Min(value = 5)
        private int minFive;
    }
}
