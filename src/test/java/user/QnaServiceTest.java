package user;

import next.CannotOperateException;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import next.service.QnaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.objenesis.instantiator.android.AndroidSerializationInstantiator;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {

    @Mock
    private QuestionDao questionDao;

    @Mock
    private AnswerDao answerDao;

    private QnaService qnaService;

    @Before
    public void setUp() {
        qnaService = new QnaService(questionDao, answerDao);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void 질문이_존재하지_않은_경우() throws CannotOperateException {
        when(questionDao.findById(1)).thenReturn(null);
        qnaService.deleteQuestion(1, new User("1", "123", "Jbee", "ad"));
    }

    @Test(expected = CannotOperateException.class)
    public void 질문한_사람과_로그인한_사람이_다른_경우() throws CannotOperateException {
        Question question = new Question("temp", "", "");
        when(questionDao.findById(1)).thenReturn(question);
        when(answerDao.findAllByQuestionId(1)).thenReturn(new ArrayList<>());
        qnaService.deleteQuestion(1, new User("1", "123", "Jbee", "ad"));
    }


    @Test
    public void 질문한_사람과_로그인한_사람이_같으면서_답변이_없는_경우() throws CannotOperateException {
        Question question = new Question();

        User user = new User("1", "123", "Jbee", "ad");
        Question newQuestion = question.newQuestion(user);

        when(questionDao.findById(0)).thenReturn(newQuestion);
        when(answerDao.findAllByQuestionId(0)).thenReturn(new ArrayList<>());
        qnaService.deleteQuestion(0, user);
    }

    @Test
    public void 질문한_사람과_로그인한_사람이_같으면서_답변의_글쓴이도_같은_경우() throws CannotOperateException {
        Question question = new Question();

        User user = new User("1", "123", "Jbee", "ad");
        Question newQuestion = question.newQuestion(user);

        when(questionDao.findById(0)).thenReturn(newQuestion);
        List<Answer> answers = new ArrayList<>();
        Answer answer = new Answer();
        answers.add(answer.newAnswer(user));

        when(answerDao.findAllByQuestionId(0)).thenReturn(new ArrayList<>());
        qnaService.deleteQuestion(0, user);
    }

    @Test
    public void 질문한_사람과_로그인한_사람이_같으면서_답변의_글쓴이가_다른_경우() throws CannotOperateException {
        Question question = new Question();

        User user = new User("1", "123", "Jbee", "ad");
        User otherUser = new User("2", "123", "aaee", "ad");
        Question newQuestion = question.newQuestion(user);

        when(questionDao.findById(0)).thenReturn(newQuestion);
        List<Answer> answers = new ArrayList<>();
        Answer answer = new Answer();
        answers.add(answer.newAnswer(otherUser));

        when(answerDao.findAllByQuestionId(0)).thenReturn(new ArrayList<>());
        qnaService.deleteQuestion(0, user);
    }
}
