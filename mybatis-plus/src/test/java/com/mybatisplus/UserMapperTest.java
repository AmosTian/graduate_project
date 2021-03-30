package com.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.mapper.UserMapper;
import com.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-03-14 21:05
 * @current mybatis-plus-com.mybatisplus
 */

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        System.out.println("-------selectAll method test-------");

        List<User> users = userMapper.selectList(null);

        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testSelectById(){
        System.out.println("通过Id查询");
        User user = userMapper.selectById(3L);//数据类型为Long，id为3

        System.out.println(user);
    }

    @Test
    public void testSelectByLike(){
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //查询数据库中 name列 包含 o 的元组
        wrapper.like("name","o");

        List<User> users = userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testSelectByLe(){
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        //查询数据库中 age列 小于 20 的元组
        wrapper.le("age",20);

        List<User> users = userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testSave(){
        User user = new User();
        user.setAge(25);
        user.setEmail("zhangsan@qq.com");
        user.setName("zhangsan");
        int count = userMapper.insert(user);
        System.out.println("新增数据成功! count=>"+count);
    }

    @Test
    public void testDeleteById(){
        userMapper.deleteById(7L);
        System.out.println("删除成功");
    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setName("lisi");
        user.setId(6L);
        userMapper.updateById(user);
        System.out.println("修改成功");
    }

    @Test
    public void testSelectPage(){
        Page<User> page = new Page<User>(2,2);
        IPage<User> userIPage = userMapper.selectPage(page,null);

        System.out.println("总条数 ------> " + userIPage.getTotal());
        System.out.println("当前页数 ------> " + userIPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + userIPage.getSize());

        List<User> records = userIPage.getRecords();
        for (User user : records) {
            System.out.println(user);
        }
    }
}
