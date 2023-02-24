package com.example.hanghaeworld;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Getter
public class HanghaeWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaeWorldApplication.class, args);
    }

}
