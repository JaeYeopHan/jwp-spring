package user;

import next.controller.user.UserController;
import next.dao.UserDao;
import next.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserDao userDao;

    private UserController uc;

    @Before
    public void setUp() {
        uc = new UserController(userDao);
    }

    @Test
    public void userCreate() throws Exception {
        when(userDao.findByUserId("1"))
                .thenReturn(new User("1", "123", "Jbee", "ljyhanll@gmail.com"));

        ExtendedModelMap model = new ExtendedModelMap();
        uc.profile("1", model);
        assertNotNull(model.get("user"));
    }

    @Test
    public void create() throws Exception {
        User user = new User("1", "123", "Jbee", "ljyhanll@gmail.com");
        uc.create(user);
        verify(userDao).insert(user);
    }
}
