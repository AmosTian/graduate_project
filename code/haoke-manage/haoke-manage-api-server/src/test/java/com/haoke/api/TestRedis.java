package com.haoke.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Set;

/**
 * @author Auspice Tian
 * @time 2021-03-30 14:16
 * @current haoke-manage-com.haoke.api
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testSave(){
        /*for (int i = 0; i < 100; i++) {
            this.redisTemplate.opsForValue().set("key_" + i, "value_"+i);
        }*/

        Set<String> keys = this.redisTemplate.keys("key_*");//选择所有的键
        for (String key : keys) {
            /*String value = (String) this.redisTemplate.opsForValue().get(key);
            System.out.println(value);*/
            this.redisTemplate.delete(key);
        }
    }
}
