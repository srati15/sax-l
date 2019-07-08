package datatypes.quiz.question;

import datatypes.quiz.Quiz;
import datatypes.quiz.answer.Answer;

import java.util.*;

public class RandomMathGenerator {
    private static final int QUESTIONS_NUM = 10;
    public static Quiz getRandomMathQuiz(){
        Quiz quiz = new Quiz();
        Map<Question, Answer> questionAnswerMap = new HashMap<>();
        Random random = new Random();
        for (int i = 0; i < QUESTIONS_NUM; i++) {
            int num = 2+random.nextInt(5);
            StringBuilder questionText = new StringBuilder();
            int sum = 0;
            char sign = '+';
            for (int j = 0; j < num; j++) {
                int current = random.nextInt(100);
                if (sign == '+')
                    sum+=current;
                else
                    sum-=current;
                questionText.append(current);
                if (random.nextBoolean())
                    sign = '+';
                else
                    sign = '-';
                if (j+1 != num) questionText.append(sign);
            }
            Question question = new QuestionResponse(questionText.toString());
            Answer answer = new Answer(String.valueOf(sum));
            questionAnswerMap.put(question, answer);
        }
        quiz.setQuestionAnswerMap(questionAnswerMap);
        return quiz;
    }

}
