package de.beosign.quizzer.util;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import de.beosign.quizzer.model.Question;

@ApplicationScoped
@Named
public class EnumUtil {
    public Question.Type[] getQuestionTypes() {
        return Question.Type.values();
    }

    public String getQuestionType(Question.Type type) {
        return FacesUtil.getResourceTextForBundle(FacesContext.getCurrentInstance(), "enums", "question.type." + type.name());
    }

}
