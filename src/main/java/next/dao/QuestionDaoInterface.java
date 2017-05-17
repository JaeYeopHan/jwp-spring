package next.dao;

import next.model.Question;

import java.util.List;

/**
 * Created by Jbee on 2017. 5. 17..
 */
public interface QuestionDaoInterface {

    Question insert(Question question);

    List<Question> findAll();

    Question findById(long questionId);

    void update(Question question);

    void delete(long questionId);

    void updateCountOfAnswer(long questionId);
}
