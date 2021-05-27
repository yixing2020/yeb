package com.xxxx.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置类
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        //String 类型key 序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //String 类型value 序列化器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //String 类型hashkey 序列化器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //String 类型hashvalue 序列化器
        redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        //将链接工厂放入到模板
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;

    }
}
