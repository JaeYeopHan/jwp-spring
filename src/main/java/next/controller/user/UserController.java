package next.controller.user;

import next.dao.UserDao;
import next.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public String form() {
        return "user/form";
    }

    @RequestMapping(value = "/users/form", method = RequestMethod.POST)
    public String create(User user) {
        userDao.insert(user);
        return "redirect:/";
    }
}
