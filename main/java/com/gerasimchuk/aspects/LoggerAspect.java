package com.gerasimchuk.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Aspect
public class LoggerAspect {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(LoggerAspect.class);

    private static final String[] SEPARATORS = {"*******************************************************",
                                                "-------------------------------------------------------",
                                                "///////////////////////////////////////////////////////",
                                                "======================================================="};
    private static final int NUM_OF_SEPARATORS = 4;
    private int currentSeparatorIndex = 0;

    @Pointcut("execution(* com.gerasimchuk.*.*.*(..))")
    public void methodCall(){}

    @Before("methodCall()")
    public void beforeMethodInvocation(){
        LOGGER.info(SEPARATORS[currentSeparatorIndex]);
        LOGGER.info("LOGGED BY ASPECTJ");
    }

    @Around("methodCall()")
    public Object aroundMethodInvocation(ProceedingJoinPoint joinPoint){
        LOGGER.info("TRYING TO INVOKE "  + joinPoint.getSignature() + " ... ");
        Object res = null;
        try {
            res = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            LOGGER.info("CATCHED EXCEPTION: " + throwable.getMessage());
        }
        LOGGER.info("INVOCATION RESULT: " + res);
        LOGGER.info("OUT FROM "  + joinPoint.getSignature() + ".");
        return res;
    }

    @After("methodCall()")
    public void afterMethodInvocation(){
        LOGGER.info(SEPARATORS[currentSeparatorIndex]);
        currentSeparatorIndex = new Random().nextInt(NUM_OF_SEPARATORS);
    }
}
