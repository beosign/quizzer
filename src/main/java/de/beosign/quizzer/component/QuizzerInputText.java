package de.beosign.quizzer.component;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.logging.Log;

/**
 * Passthrough attributes are automatically rendered for custom components, at least
 * when using Mojarra. When adding an html element, the passthrough attributes will get rendered automatically.
 * 
 * @author Florian Dahlmanns
 *
 */
@FacesComponent(value = QuizzerInputText.COMPONENT_TYPE)
public class QuizzerInputText extends UIComponentBase {
    public static final String COMPONENT_TYPE = "de.beosign.quizzer.component.QuizzerInputText";

    private static final Logger LOGGER = Log.logger(QuizzerInputText.class);

    @Override
    public String getFamily() {
        return "de.beosign.quizzer.component.inputtext";
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        super.encodeBegin(context);

        Map<String, Object> passThruMap = getPassThroughAttributes(true);
        LOGGER.debug("Passthru: " + passThruMap);
        LOGGER.debug("Renderer: " + getRenderer(context));

        ResponseWriter writer = context.getResponseWriter();
        String value = (String) getAttributes().get("value");

        writer.startElement("input", this);
        if (value != null) {
            writer.writeAttribute("value", value, "value");
        }

        LOGGER.debug("Writer class: " + writer.getClass().getName());
        writer.startElement("dummy", null);

    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        super.encodeEnd(context);

        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("dummy");
        // The endMethod finally writes the passthrough attributes
        // (at least SUN's faces implementation com.sun.faces.renderkit.html_basic.HtmlResponseWriter)
        writer.endElement("input");

    }

}
