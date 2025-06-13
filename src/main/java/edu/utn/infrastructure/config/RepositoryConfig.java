package edu.utn.infrastructure.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RepositoryConfig {

    @Bean
    @Profile("firebase")
    public CommandLineRunner firebaseRepoLogger() {
        return args -> System.out.println("🔥 Usando FirebaseDeckService como IDeckRepository");
    }

    @Bean
    @Profile("postgres")
    public CommandLineRunner postgresRepoLogger() {
        return args -> System.out.println("🐘 Usando PostgresDeckRepository como IDeckRepository");
    }
}

