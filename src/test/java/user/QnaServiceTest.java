package user;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.service.QnaService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

    

}
