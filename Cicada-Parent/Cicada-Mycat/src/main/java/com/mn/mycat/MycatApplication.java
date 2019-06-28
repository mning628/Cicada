package com.mn.mycat;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mn.mycat.mapper")
public class MycatApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(MycatApplication.class, args);
    }
}
