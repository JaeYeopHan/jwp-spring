package next.dao;

import next.model.Answer;

import java.util.List;

/**
 * Created by Jbee on 2017. 5. 17..
 */
public interface AnswerDaoInterface {

    Answer insert(Answer answer);

    Answer findById(long answerId);

    List<Answer> findAllByQuestionId(long questionId);

    void delete(Long answerId);
}
