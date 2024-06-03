package com.zawadzkia.springtodo;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringTodoApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringTodoApplication.class);
        if (SystemUtils.IS_OS_WINDOWS) {
            app.setAdditionalProfiles("h2");
        }
        app.run(args);
    }

}
