package cn.lmx.basic.echo.aspect;

import cn.lmx.basic.annotation.echo.EchoResult;
import cn.lmx.basic.interfaces.echo.EchoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * InjectionResult 注解的 AOP 工具
 *
 * @author lmx
 * @date 2023/7/4 14:27
 */
@Aspect
@AllArgsConstructor
@Slf4j
public class EchoResultAspect {
    private final EchoService echoService;


    @Pointcut("@annotation(cn.lmx.basic.annotation.echo.EchoResult)")
    public void methodPointcut() {
    }


    @Around("methodPointcut()&&@annotation(ir)")
    public Object interceptor(ProceedingJoinPoint pjp, EchoResult ir) throws Throwable {
        Object proceed = pjp.proceed();
        echoService.action(proceed, ir.ignoreFields());
        return proceed;
    }
}
