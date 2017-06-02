package next.interceptor;

import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.Base64;

public class BasicAuthInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(BasicAuthInterceptor.class);
    public static final String USER_SESSION_KEY = "user";

    @Autowired
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        logger.debug("header: {}", request.getHeader("Authorization"));
        String value = request.getHeader("Authorization");
        logger.debug("value: {}", value);

        String basedValue = value.substring(6, value.length()).trim();
        logger.debug("basedValue: {}", basedValue);

        String credentials = new String(Base64.getDecoder().decode(basedValue), Charset.forName("UTF-8"));
        logger.debug("credentials: {}", credentials);


        String[] userInfo = credentials.split(":");
        String userId = userInfo[0];
        String password = userInfo[1];
        logger.debug("userId: {}", userId);
        logger.debug("password: {}", password);

        User user = userDao.findByUserId(userId);
        if (user == null) {
            return false;
        }

        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLogined(session)) {
            session.setAttribute(USER_SESSION_KEY, user);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
    }
}
