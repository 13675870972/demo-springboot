package com.example.demospringboot.action;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author cyc
 */
@SpringBootApplication
@ComponentScan(basePackages = "com")
public class DemoSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoSpringbootApplication.class, args);
    }
}
