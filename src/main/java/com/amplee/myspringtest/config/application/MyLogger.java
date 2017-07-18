package com.amplee.myspringtest.config.application;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyLogger {

    @Pointcut("execution(* *(..)) && within(com.amplee.myspringtest.config.application.*)") //the pointcut expression
    public void getAnyValue() {} //the pointcut signature

    @AfterReturning(pointcut = "getAnyValue()", returning = "obj")
    public void printValue(Object obj) {
        System.out.println(obj);
    }

    @Before("getAnyValue()")
    public void init() {
        System.out.println("init");
    }

    @After("getAnyValue()")
    public void close() {
        System.out.println("close");
    }
}
