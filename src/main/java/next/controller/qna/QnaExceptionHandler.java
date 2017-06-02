package next.controller.qna;

import next.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class QnaExceptionHandler {
    @ExceptionHandler(value = AuthorizationException.class)
    public String basicAuthExceptionHandler() {
        return "redirect:/users/loginForm";
    }
}
