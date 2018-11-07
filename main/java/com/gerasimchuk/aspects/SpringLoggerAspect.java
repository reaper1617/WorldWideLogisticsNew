package com.gerasimchuk.aspects;//package com.gerasimchuk.aspects;
//
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
//@Component
//@Aspect
//public class SpringLoggerAspect {
//
//    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SpringLoggerAspect.class);
//
//    @Pointcut("execution(* com.gerasimchuk.services.interfaces.CargoService.createCargo(..))")
//    public void pointcut(){};
//
//    @Before("pointcut()")
//    public void beforeMethod(){
//        LOGGER.info("LOGGED BY ASPECTJ: BEFORE");
//    }
//
//    @After("pointcut()")
//    public void AfterMethod(){
//        LOGGER.info("LOGGED BY ASPECTJ: AFTER");
//    }
//
//}
