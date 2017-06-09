package next.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class PerformanceAspect {
    private Logger log = LoggerFactory.getLogger(PerformanceAspect.class);

    @Pointcut("within(next.controller..*)" +
            "|| within(next.dao..*)" +
            "|| within(next.service..*)")
    public void performancePointCut() {

    }

    @Around("performancePointCut()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("execution:: {}", pjp.getTarget());
        StopWatch sw = new StopWatch();
        sw.start();
        Object retValue = pjp.proceed();
        sw.stop();
        log.debug("execution time:: {}", sw.getTotalTimeMillis());
        return retValue;
    }

    @Pointcut("within(next..*)")
    public void loggingPointcut() {

    }

    @Around("loggingPointcut()")
    public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {

        final String methodName = pjp.getSignature().getName();
        log.debug("{}(): {}", methodName, pjp.getArgs());
        Object result = pjp.proceed();
        log.debug("{}(): result={}", methodName, result);

        return result;
    }
}
