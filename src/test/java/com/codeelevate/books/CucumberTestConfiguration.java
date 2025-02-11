package com.codeelevate.books;


import com.codeelevate.books.config.RedisTestConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Import(RedisTestConfiguration.class)  // Importa a configuração do mock do Redis
public class CucumberTestConfiguration {
}