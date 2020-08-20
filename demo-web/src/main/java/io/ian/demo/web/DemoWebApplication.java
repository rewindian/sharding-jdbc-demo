package io.ian.demo.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"io.ian.demo"})
public class DemoWebApplication extends SpringBootServletInitializer {

    private static Class<DemoWebApplication> appClass = DemoWebApplication.class;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(appClass);
    }

    public static void main(String[] args) {
        SpringApplication.run(appClass, args);
    }
}
