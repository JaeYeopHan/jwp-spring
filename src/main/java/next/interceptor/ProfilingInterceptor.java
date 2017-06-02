package next.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfilingInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ProfilingInterceptor.class);

    private static final String TIME = "time";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute(TIME, startTime);
        logger.debug("{}:: startTime: {}", request.getRequestURL(), startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        long startTime = (long) request.getAttribute(TIME);
        long endTime = System.currentTimeMillis();
        logger.debug("{}:: endTime: {}", request.getRequestURL(), endTime);
        logger.debug("{}:: performance: {}", request.getRequestURL(), endTime - startTime);
    }
}
