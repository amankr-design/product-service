package com.aman.ecommerce.productservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.aman.ecommerce.productservice.service.*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable{

        logger.info("Entering Method: {}", joinPoint.getSignature().getName());

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        logger.info("Exiting Method: {}", joinPoint.getSignature().getName());

        logger.info("Execution Time:{} ms", (end - start));

        return result;
    }

}
