package de.beosign.quizzer.controller;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.apache.logging.log4j.Logger;

import de.beosign.quizzer.model.Answer;
import de.beosign.quizzer.model.BooleanExamQuestionAnswer;
import de.beosign.quizzer.model.Course;
import de.beosign.quizzer.model.Exam;
import de.beosign.quizzer.model.ExamQuestion;
import de.beosign.quizzer.model.ExamQuestionAnswer;
import de.beosign.quizzer.model.TextExamQuestionAnswer;
import de.beosign.quizzer.service.CourseService;
import de.beosign.quizzer.service.ExamService;

@Named
@ConversationScoped
public class TakeExamController implements Serializable {
    private static final long serialVersionUID = 1L;
    private static AtomicLong instanceCounter = new AtomicLong(0);

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

    @Inject
    private Conversation conversation;

    @EJB
    private CourseService courseService;

    @EJB
    private ExamService examService;

    private Course course;

    private Exam exam;

    private String courseName;

    private ExamQuestion currentQuestion;
    private int currentQuestionIndex;

    /**
     * One must use an empty {@link String} as action result in order to allow JSF to refresh the page correctly, not the same jsf page. When returning
     * <code>exam</code>> without redirecting, the button click only worked on each second click!!
     * 
     * @author Florian Dahlmanns
     *
     */
    public enum Pages {
        START("start-exam"), NEXT(""), PREV(""), CANCEL("index"), SELF("exam?faces-redirect=true"), BACK("index"), FINISH("finish-exam");

        private final String outcome;

        Pages(String outcome) {
            this.outcome = outcome;
        }

        public String getOutcome() {
            return outcome;
        }
    }

    @PostConstruct
    private void init() {
        logger.debug("Creating instance {}", instanceCounter.incrementAndGet());
    }

    public void initConversation() {
        if (!facesContext.isPostback() && conversation.isTransient()) {
            conversation.begin();
            logger.debug("Conversation has started");
        }
    }

    public String endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        return Pages.BACK.getOutcome();
    }

    @Transactional
    public String startExam() {
        gotoQuestion(0);
        return Pages.SELF.outcome;
    }

    public String finishExam() {
        return Pages.FINISH.getOutcome();
    }

    @Transactional
    public String nextQuestion() {
        // save changes by manually updating each single answer
        for (ExamQuestionAnswer a : currentQuestion.getGivenAnswers()) {
            if (a instanceof BooleanExamQuestionAnswer) {
                logger.debug("Answer: " + ((BooleanExamQuestionAnswer) a).isCorrect());

            }
            examService.updateExamQuestionAnswer(a);
        }

        // TODO The following line throws an exception: Detached entity passed to persist: Exam????
        // examService.update(exam);

        currentQuestionIndex++;
        logger.debug("Jumping to next question {}", currentQuestionIndex);
        return gotoQuestion(currentQuestionIndex);
    }

    @Transactional
    public String prevQuestion() {

        currentQuestionIndex--;
        logger.debug("Jumping to prev question {}", currentQuestionIndex);
        return gotoQuestion(currentQuestionIndex);
    }

    public String gotoQuestion(int index) {
        if (index < 0) {
            index = 0;
        }

        logger.debug("Jumping to question {}", index);
        currentQuestionIndex = index;

        exam = examService.find(exam.getId()).get();
        currentQuestion = exam.getExamQuestions().get(currentQuestionIndex);

        if (currentQuestion.getGivenAnswers().isEmpty()) {

            switch (currentQuestion.getQuestion().getType()) {
            case TEXT:
                TextExamQuestionAnswer textAnswer = new TextExamQuestionAnswer(currentQuestion, currentQuestion.getQuestion().getAnswers().get(0), null);
                currentQuestion.getGivenAnswers().add(textAnswer);
                break;
            case SINGLE:
            case MULTIPLE:
                for (Answer a : currentQuestion.getQuestion().getAnswers()) {
                    BooleanExamQuestionAnswer boolAnswer = new BooleanExamQuestionAnswer(currentQuestion, a, false);
                    currentQuestion.getGivenAnswers().add(boolAnswer);
                }
            default:
                break;
            }
            // Collections.shuffle(currentQuestion.getGivenAnswers());
        }

        return Pages.NEXT.getOutcome();
    }

    @Transactional
    public void initAfterViewParams() {
        if (courseName != null && exam == null) {
            logger.debug("Taking exam for course {}", courseName);

            course = courseService.find(courseName).orElseThrow(() -> new IllegalArgumentException("No course found with name " + courseName));
            exam = courseService.createExam(course);

            logger.info("Created exam {} from course {}", exam, course);

        } else if (exam == null) {
            throw new IllegalArgumentException("No courseName found in GET parameter");
        }
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ExamQuestion getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(ExamQuestion currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

}
