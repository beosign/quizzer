package de.beosign.quizzer.util;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class LayoutController {

    public String getHeaderStyle() {
        return "class=\"title ui-widget-header ui-corner-all\"";
    }
}
