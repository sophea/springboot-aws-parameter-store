package com.sma.parameterstore.ssm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.*;

@Slf4j
@SpringBootApplication(exclude = {ContextResourceLoaderAutoConfiguration.class,
    ContextCredentialsAutoConfiguration.class,
    ContextStackAutoConfiguration.class,
    ContextInstanceDataAutoConfiguration.class,
    })
public class SsmApplication implements CommandLineRunner {

    @Value("${db.username:test}")
    private String dbUserName;

    @Value("${categories.types:#{null}}")
    private String[] categories;

    public static void main(String[] args) {

        SpringApplication.run(SsmApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Value dbUsername : {}", dbUserName);
        log.info("categories : {} - {} ", categories.length, categories);
    }
}
