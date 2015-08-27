package de.beosign.cashflow.quizzer.validator;

import static org.junit.Assert.*;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.beosign.quizzer.validator.ValidationUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = FacesContext.class)
public class ValidationUtilTest {

    /**
     * Unit under test.
     * private ValidationUtil validationUtil;
     */

    private FacesContext facesContext;

    @Before
    public void setup() {
        facesContext = PowerMockito.mock(FacesContext.class);

    }

    @Test
    public void addValidationErrorsToFacesContextNoError() {
        ClassToBeValidated c = new ClassToBeValidated();
        c.minFive = 5;
        c.notNullString = "Hi";

        List<FacesMessage> messages = ValidationUtil.addValidationErrorsToFacesContext(c, facesContext);
        assertEquals("No messages expected", 0, messages.size());
    }

    @Test
    public void addValidationErrorsToFacesContextSingleError() {
        ClassToBeValidated c = new ClassToBeValidated();
        c.minFive = 2;
        c.notNullString = "Hi";

        List<FacesMessage> messages = ValidationUtil.addValidationErrorsToFacesContext(c, facesContext);
        assertEquals("Exactly one message expected", 1, messages.size());

    }

    @Test
    public void addValidationErrorsToFacesContextMultipleErrors() {
        ClassToBeValidated c = new ClassToBeValidated();
        c.minFive = 2;
        c.notNullString = null;

        List<FacesMessage> messages = ValidationUtil.addValidationErrorsToFacesContext(c, facesContext);
        assertEquals("Two messages expected", 2, messages.size());

    }

    private static class ClassToBeValidated {
        @NotNull
        private String notNullString;

        @Min(value = 5)
        private int minFive;
    }
}
