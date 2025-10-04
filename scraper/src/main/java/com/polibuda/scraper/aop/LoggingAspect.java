package com.polibuda.scraper.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    private static final String SERVICE_METHODS = "execution(public * com.polibuda.scraper.service..*(..))";

    @Before(SERVICE_METHODS)
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("Wejście do metody: {}", methodName);
    }

    @AfterReturning(pointcut = SERVICE_METHODS, returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("Wyjście z metody: {} z wynikiem: {}", methodName, result);
    }

    @AfterThrowing(pointcut = SERVICE_METHODS, throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("Metoda {} zakończyła się wyjątkiem: {}", methodName, ex.getMessage(), ex);
    }
}
