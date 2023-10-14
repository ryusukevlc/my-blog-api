package info.ryusukeblog.myblogapi.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class MapperAspect {

    @Before("execution(* info.ryusukeblog.myblogapi.repository.*Mapper.save*(..)) ||" +
            "execution(* info.ryusukeblog.myblogapi.repository.*Mapper.update*(..))")
    public void setDatetimeProperty(JoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getMethod().getName();

        LocalDateTime now = LocalDateTime.now();

        Object[] args = joinPoint.getArgs();
        Object model = args[0];

        if (methodName.startsWith("save")) {
            this.setCreatedAt(now, model);
            this.setUpdatedAt(now, model);
        } else if (methodName.startsWith("update")) {
            this.setUpdatedAt(now, model);
        }
    }

    private void setCreatedAt(LocalDateTime now, Object model) throws Throwable {
        Method setCreatedAt = ReflectionUtils.findMethod(model.getClass(), "setCreatedAt", LocalDateTime.class);
        if (setCreatedAt != null) {
            setCreatedAt.invoke(model, now);
        }
    }

    private void setUpdatedAt(LocalDateTime now, Object model) throws Throwable {
        Method setUpdatedAt = ReflectionUtils.findMethod(model.getClass(), "setUpdatedAt", LocalDateTime.class);
        if (setUpdatedAt != null) {
            setUpdatedAt.invoke(model, now);
        }
    }
}
