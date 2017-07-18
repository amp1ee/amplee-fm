package com.amplee.myspringtest.config.application;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        ctx.register(SomeService.class);
        ctx.register(MyLogger.class);

        ctx.refresh();
        SomeService service = ctx.getBean(SomeService.class);

        service.getDoubleValue();
    }
}
