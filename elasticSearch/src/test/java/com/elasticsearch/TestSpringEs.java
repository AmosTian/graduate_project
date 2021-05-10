package com.elasticsearch;

import com.elasticsearch.pojo.User;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-05-10 14:12
 * @current elasticSearch-com.es
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSpringEs {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testSave(){
        User user = new User();
        user.setId(1001L);
        user.setName("张三");
        user.setAge(11);
        user.setHobby("足球 篮球 听音乐");

        IndexQuery indexQuery = new IndexQueryBuilder().withObject(user).build();
        String res = this.elasticsearchTemplate.index(indexQuery);

        System.out.println(res);
    }

    @Test
    public void bulk(){
        List list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setId(1001L + i);
            user.setAge(i % 50 + 10);
            user.setName("张三" + i);
            user.setHobby("足球、篮球、听音乐");

            IndexQuery indexQuery = new IndexQueryBuilder().withObject(user).build();

            list.add(indexQuery);
        }
        this.elasticsearchTemplate.bulkIndex(list);
    }

    /*
    * 局部更新数据
    * */
    @Test
    public void update(){
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source("age","30");
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withId("1003")//根据id选择
                .withClass(User.class)//更新的类型
                .withIndexRequest(indexRequest)//指定更新内容
                .build();

        UpdateResponse response = this.elasticsearchTemplate.update(updateQuery);

        System.out.println(response.getResult());
        System.out.println(response.getIndex());
        System.out.println(response.toString());
    }

    /*
    * 删除数据
    * */
    @Test
    public void delete(){
        String delete = this.elasticsearchTemplate.delete(User.class, "1002");

        System.out.println(delete);
    }

    /*
    * 搜索
    * */
    @Test
    public void search(){
        PageRequest pageRequest = PageRequest.of(1,10);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name","张三"))//match查询
                .withPageable(pageRequest)
                .build();

        AggregatedPage<User> users = this.elasticsearchTemplate.queryForPage(searchQuery,User.class);

        System.out.println("总页数"+users.getTotalPages());//获取总页数
        for (User user : users) {
            System.out.println(user);
        }
    }

}
