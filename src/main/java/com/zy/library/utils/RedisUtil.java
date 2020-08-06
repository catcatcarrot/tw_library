package com.zy.library.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisUtil {

    @Autowired
    @Qualifier("myRedisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    public Double incrementScore(String key,Object value,double score){
        return redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    public Set<Object> reverseRange(String key, long start, long end){
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }


}
