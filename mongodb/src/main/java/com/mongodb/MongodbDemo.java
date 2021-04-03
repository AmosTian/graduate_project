package com.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


/**
 * @author Auspice Tian
 * @time 2021-04-01 13:23
 * @current mongodb-com.mongodb
 */
public class MongodbDemo {

    public static void main(String[] args) {
        //建立连接
        MongoClient mongoClient = MongoClients.create("mongodb://8.140.130.91:27017");

        //选择数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("testdb");

        //选择表
        MongoCollection<Document> userCollection = mongoDatabase.getCollection("user");

        //查询表
        for (Document document : userCollection.find().limit(10)) {
            System.out.println(document.toJson());
        }

        mongoClient.close();
    }

}
