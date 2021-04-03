package com.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Auspice Tian
 * @time 2021-04-01 20:58
 * @current mongodb-com.mongodb
 */
public class TestCRUD {

    private MongoCollection<Document> mongoCollection;

    @Before
    public void init(){
        //建立连接
        MongoClient mongoClient = MongoClients.create("mongodb://8.140.130.91:27017");

        //选择数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("testdb");

        //选择表
        this.mongoCollection = mongoDatabase.getCollection("users");
    }

    // 复杂查询age<=50并且id>=100的用户信息，并且按照id倒序排序，只返回id，age字段，不返回_id字段
    @Test
    public void testQuery(){
        for (Document document : this.mongoCollection.find(
                Filters.and(
                        Filters.lte("age",50),
                        Filters.gte("id",100)
                )
        ).sort(Sorts.descending("id"))//降序
        .projection(
                Projections.fields(
                        Projections.include("id","age"),
                        Projections.excludeId()//排除ObjectID
                )
        )
        ) {
            System.out.println(document);
        }
    }

    @Test
    public void testInsert(){
        Document document = new Document("id",10002)
                .append("name", "张三")
                .append("age", 30);
        this.mongoCollection.insertOne(document);
        System.out.println("插入数据");

        for (Document document1 : this.mongoCollection.find(Filters.eq("id",10002))) {
            System.out.println(document1.toJson());
        }
    }

    @Test
    public void testDelete(){
        DeleteResult result = this.mongoCollection.deleteOne(Filters.eq("id",10002));

        System.out.println(result);
    }

    @Test
    public void testUpdate(){
        UpdateResult result = this.mongoCollection.updateOne(
                Filters.eq("id",10001), Updates.set("age",25)
        );

        System.out.println(result);

        for (Document doc : this.mongoCollection.find(Filters.eq("id", 10001))) {
            System.out.println(doc.toJson());
        }
    }

}
