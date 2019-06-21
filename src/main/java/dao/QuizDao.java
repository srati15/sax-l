package dao;

import dao.entity.EntityManager;
import datatypes.Quiz;
import enums.DaoType;

import java.util.Collection;
import java.util.List;

public class QuizDao implements Dao<Integer, Quiz> {
    private EntityManager entityManager = EntityManager.getInstance();


    private final QuestionDao questionDao;
    private final AnswerDao answerDao;
    private Cao<Integer, Quiz> cao = new Cao<>();

    public QuizDao(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    public AnswerDao getAnswerDao() {
        return answerDao;
    }

    @Override
    public Quiz findById(Integer id) {
        return cao.findById(id);
    }

    @Override
    public void insert(Quiz entity) {
        if (entityManager.getPersister().executeInsert(entity)) {
            System.out.println("Quiz inserted sucessfully");
            cao.add(entity);
        }else {
            System.out.println("Error inserting quiz");
        }
    }

    @Override
    public Collection<Quiz> findAll() {
        return cao.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        // TODO: 6/17/19
    }

    @Override
    public void update(Quiz entity) {

        // TODO: 6/2/19
    }

    @Override
    public DaoType getDaoType() {
        return DaoType.Quiz;
    }

    @Override
    public void cache() {
        List<Quiz> quizzes = entityManager.getFinder().executeFindAll(Quiz.class);
        quizzes.forEach(s->cao.add(s));
    }

}
