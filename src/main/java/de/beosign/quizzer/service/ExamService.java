package de.beosign.quizzer.service;

import de.beosign.quizzer.model.Exam;
import de.beosign.quizzer.model.ExamQuestion;
import de.beosign.quizzer.model.ExamQuestionAnswer;

public interface ExamService extends Service<Exam, Long> {
    ExamQuestion updateExamQuestion(ExamQuestion examQuestion);

    ExamQuestionAnswer updateExamQuestionAnswer(ExamQuestionAnswer examQuestionAnswer);
}
