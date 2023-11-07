package info.ryusukeblog.myblogapi.global.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* info.ryusukeblog.myblogapi..*.*(..))")
    public Object outputLogs(ProceedingJoinPoint jp) throws Throwable {
        try {
            logger.info("method start: " + jp.getSignature());
            Object result = jp.proceed();
            logger.info("method end: " + jp.getSignature());
            return result;
        } catch (Exception e) {
            logger.error("throw exception by: " + jp.getSignature());
            e.printStackTrace();
            throw e;
        }
    }

}
