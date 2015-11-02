package de.beosign.quizzer.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;

@Named
@SessionScoped
public class LoginController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Logger logger;

    @Inject
    private HttpServletRequest request;

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public enum Pages {
        OK("menu"), LOGIN_FAILED();

        private final String outcome;

        Pages() {
            this("");
        }

        Pages(String outcome) {
            this.outcome = outcome;
        }

        public String getOutcome() {
            return outcome;
        }
    }

    public String login() {
        HttpSession session = request.getSession();
        logger.debug("Created session {} for user {}", session.getId(), getUsername());

        return Pages.OK.getOutcome();
    }

}
