package edu.utn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"edu.utn.infrastructure.ports.out"})
@EntityScan(basePackages = {"edu.utn.infrastructure.adapters.out.persistence.entities"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}