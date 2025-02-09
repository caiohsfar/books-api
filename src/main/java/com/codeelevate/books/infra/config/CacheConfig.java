package com.codeelevate.books.infra.config;

import com.codeelevate.books.domain.model.Book;
import com.codeelevate.books.infra.database.entity.BookEntity;
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


@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisConnectionFactory jedisConnectionFactory(@Value("${spring.redis.host}") String host,
                                                         @Value("${spring.redis.port}") int port) {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(host, port);
        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder().build();
        return new JedisConnectionFactory(redisConfig, clientConfig);
    }

//    @Bean
//    public Jedis jedis(@Value("${spring.redis.host}") String host,
//                                        @Value("${spring.redis.port}") int port) {
//        return new Jedis(host, port);
//    }


//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(factory);
//        return template;
//    }

    @Bean
    public RedisTemplate<String, Book> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Book> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Book.class));

        template.afterPropertiesSet();

        template.setConnectionFactory(connectionFactory);


        return template;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1))
                .disableCachingNullValues();
    }


    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory,
                                          @Value("${spring.redis.ttlMinutes.bookList}") int bookListTtl,
                                          @Value("${spring.redis.ttlMinutes.book}") int bookTtl,
                                          @Value("${spring.redis.ttlMinutes.recently}") int recentlyTtl) {


        Map<String, RedisCacheConfiguration> cachesConfigs = Map.of(
                "bookList", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(bookListTtl)),
                "book", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(bookTtl)),
                "recently", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(recentlyTtl))
        );


        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(connectionFactory)
                .withInitialCacheConfigurations(cachesConfigs)
                .build();

    }
}


