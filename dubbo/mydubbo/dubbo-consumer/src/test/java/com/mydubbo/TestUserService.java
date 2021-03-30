package com.mydubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mydubbo.pojo.User;
import com.mydubbo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-03-12 15:24
 * @current mydubbo-com.mydubbo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserService {
    @Reference(version = "1.0.0")
    private UserService userService;

    @Test
    public void testQueryAll(){
        for (int i = 0; i < 1000; i++) {
            List<User> users = this.userService.queryAll();

            for (User user : users) {
                System.out.println(user);
            }
        }
    }
}
