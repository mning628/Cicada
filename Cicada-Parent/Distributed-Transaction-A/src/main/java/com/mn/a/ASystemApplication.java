package com.mn.a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ASystemApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(ASystemApplication.class, args);
    }
}
