package com.codeelevate.books.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;

import redis.embedded.RedisServer;

import java.io.IOException;

@TestConfiguration
public class RedisTestConfiguration {

    private RedisServer redisServer;

    public RedisTestConfiguration(@Value("${spring.redis.host}") String host,
                                  @Value("${spring.redis.port}") int port) throws IOException {
        this.redisServer = new RedisServer(port);
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() throws IOException {
        redisServer.stop();
    }
}