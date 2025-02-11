package com.codeelevate.books.infra.config;

import com.codeelevate.books.domain.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;

/**
 * Cache configuration for the Books API.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configures the Redis connection factory.
     *
     * @param host the Redis host
     * @param port the Redis port
     * @return the Redis connection factory
     */
    @Bean
    public RedisConnectionFactory jedisConnectionFactory(@Value("${spring.redis.host}") String host,
                                                         @Value("${spring.redis.port}") int port) {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(host, port);
        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder().build();
        return new JedisConnectionFactory(redisConfig, clientConfig);
    }

    /**
     * Configures the Redis template for Book objects.
     *
     * @param connectionFactory the Redis connection factory
     * @return the Redis template
     */
    @Bean
    public RedisTemplate<String, Book> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Book> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Book.class));
        template.afterPropertiesSet();
        return template;
    }

    /**
     * Configures the Redis cache configuration.
     *
     * @return the Redis cache configuration
     */
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1))
                .disableCachingNullValues();
    }

    /**
     * Configures the Redis cache manager.
     *
     * @param connectionFactory the Redis connection factory
     * @param bookListTtl the TTL for the book list cache
     * @param bookTtl the TTL for the book cache
     * @return the Redis cache manager
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory,
                                          @Value("${spring.redis.ttlMinutes.bookList}") int bookListTtl,
                                          @Value("${spring.redis.ttlMinutes.book}") int bookTtl) {
        Map<String, RedisCacheConfiguration> cachesConfigs = Map.of(
                "bookList", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(bookListTtl)),
                "book", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(bookTtl))
        );
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(connectionFactory)
                .withInitialCacheConfigurations(cachesConfigs)
                .build();
    }

}