package com.es;

import com.es.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Formatter;

/**
 * @author Auspice Tian
 * @time 2021-05-10 13:03
 * @current es-restapi-com.es
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSpringES {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void save(){
        User user = new User();
        user.setId(1001L);
        user.setName("张三");
        user.setAge(11);
        user.setHobby("足球 篮球 听音乐");


        IndexQuery indexQuery = new IndexQueryBuilder().withObject(user).build();
        String res = this.elasticsearchTemplate.index(indexQuery);

        System.out.println(res);
    }
}
