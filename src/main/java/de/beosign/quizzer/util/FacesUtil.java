package de.beosign.quizzer.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import de.beosign.quizzer.logging.Log;

public class FacesUtil {

    public static String getResourceText(FacesContext facesContext, String key, Object... args) {
        return getResourceTextForBundle(facesContext, "msg", key, args);
    }

    public static String getResourceTextForBundle(FacesContext facesContext, String bundleName, String key, Object... args) {
        Application app = facesContext.getApplication();
        ResourceBundle bundle = app.getResourceBundle(facesContext, bundleName);
        return getResourceText(bundle, key, args);
    }

    public static String getResourceText(ResourceBundle bundle, String key, Object... args) {
        String text;
        try {
            text = bundle.getString(key);
        } catch (MissingResourceException e) {
            Log.logger().error("Could not find labels resource '" + key + "'");
            return "???" + key + "???";
        }
        if (args != null) {
            text = MessageFormat.format(text, args);
        }
        return text;
    }

    public static FacesMessage getFacesMessage(FacesContext ctx, FacesMessage.Severity severity, String msgKey, Object... args) {
        Locale loc = ctx.getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle(ctx.getApplication().getMessageBundle(), loc);
        String msg = bundle.getString(msgKey);
        if (args != null) {
            MessageFormat format = new MessageFormat(msg);
            msg = format.format(args);
        }
        return new FacesMessage(severity, msg, null);
    }

    public static Locale getCurrentLocale() {
        return FacesContext.getCurrentInstance().getViewRoot().getLocale();

    }

    public static String getComponentLabel(FacesContext facesContext, UIComponent component) {
        Object label = component.getAttributes().get("label");
        if (label != null && (label instanceof String) && ((String) label).length() == 0) {
            ValueExpression expression = component.getValueExpression("label");
            if (expression != null) {
                if (expression != null) {
                    Object objectExp = expression.getValue(facesContext.getELContext());
                    if (objectExp != null) {
                        return objectExp.toString();
                    }
                }

            }

        }

        if (label != null) {
            return label.toString();
        } else {
            return component.getClientId(facesContext);
        }
    }

}
