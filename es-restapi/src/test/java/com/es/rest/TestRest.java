package com.es.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Auspice Tian
 * @time 2021-05-09 23:05
 * @current es-restapi-com.es.rest
 */
public class TestRest {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private RestClient restClient;

    @Before
    public void init(){
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("8.140.130.91",9200,"http"),
                new HttpHost("8.140.130.91",9201,"http"),
                new HttpHost("8.140.130.91",9202,"http")
        );

        //创建失败
        builder.setFailureListener(new RestClient.FailureListener(){
            @Override
            public void onFailure(Node node) {
                System.out.println("出错了=>"+node);
            }
        });

        this.restClient = builder.build();
    }

    @After
    public void after() throws IOException {
        //应用关闭，客户端关闭
        restClient.close();
    }

    // 测试集群状态
    @Test
    public void getInfo() throws IOException {
        Request request = new Request("GET","/_cluster/health");
        request.addParameter("pretty","true");

        Response response = this.restClient.performRequest(request);

        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));

        /*
        * HTTP/1.1 200 OK
        {
          "cluster_name" : "es-haoke-cluster",
          "status" : "green",
          "timed_out" : false,
          "number_of_nodes" : 3,
          "number_of_data_nodes" : 3,
          "active_primary_shards" : 6,
          "active_shards" : 12,
          "relocating_shards" : 0,
          "initializing_shards" : 0,
          "unassigned_shards" : 0,
          "delayed_unassigned_shards" : 0,
          "number_of_pending_tasks" : 0,
          "number_of_in_flight_fetch" : 0,
          "task_max_waiting_in_queue_millis" : 0,
          "active_shards_percent_as_number" : 100.0
        }
        * */
    }

    //新增数据
    @Test
    public void save() throws Exception {
        //构造数据
        Map<String,Object> data = new HashMap<>();
        data.put("id",2001);
        data.put("title","asd");
        data.put("price",2001);

        Request request = new Request("POST","/haoke/house");
        request.addParameter("pretty","true");
        request.setJsonEntity(MAPPER.writeValueAsString(data));

        Response response = this.restClient.performRequest(request);

        //状态码
        System.out.println(response.getStatusLine());
        //获取返回数据
        System.out.println(EntityUtils.toString(response.getEntity()));

        /*
        HTTP/1.1 201 Created
        {
            "_index" : "haoke",
                "_type" : "house",
                "_id" : "5evNU3kB6bIFS3djvG3Y",
                "_version" : 1,
                "result" : "created",
                "_shards" : {
            "total" : 2,
                    "successful" : 2,
                    "failed" : 0
        },
            "_seq_no" : 0,
                "_primary_term" : 3
        }
        */
    }

    //根据id查询数据
    @Test
    public void getById() throws IOException {
        Response response = this.restClient.performRequest(new Request("GET", "/haoke/house/5OvNU3kB6bIFS3djOG3t"));

        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));

        /*
        * HTTP/1.1 200 OK
        * {
        *   "_index":"haoke",
        *   "_type":"house",
        *   "_id":"5OvNU3kB6bIFS3djOG3t",
        *   "_version":1,
        *   "found":true,
        *   "_source":{
        *       "price":2001,
        *       "id":2001,
        *       "title":"asd"
        *   }
        * }
         * */
    }

    //搜索数据
    @Test
    public void search() throws IOException {
        Request request = new Request("POST","/haoke/house/_search");
        String searchJson = "{\"query\": {\"match\": {\"title\": \"asd\"}}}";
        request.setJsonEntity(searchJson);
        request.addParameter("pretty","true");

        Response response = this.restClient.performRequest(request);

        System.out.println(response.getStatusLine());
        System.out.println(EntityUtils.toString(response.getEntity()));

        /*
        * HTTP/1.1 200 OK
        {
          "took" : 74,
          "timed_out" : false,
          "_shards" : {
            "total" : 6,
            "successful" : 6,
            "skipped" : 0,
            "failed" : 0
          },
          "hits" : {
            "total" : 0,
            "max_score" : null,
            "hits" : [ ]
          }
        }
        *
        * HTTP/1.1 200 OK
        {
          "took" : 10,
          "timed_out" : false,
          "_shards" : {
            "total" : 6,
            "successful" : 6,
            "skipped" : 0,
            "failed" : 0
          },
          "hits" : {
            "total" : 2,
            "max_score" : 0.2876821,
            "hits" : [
              {
                "_index" : "haoke",
                "_type" : "house",
                "_id" : "5OvNU3kB6bIFS3djOG3t",
                "_score" : 0.2876821,
                "_source" : {
                  "price" : 2001,
                  "id" : 2001,
                  "title" : "asd"
                }
              },
              {
                "_index" : "haoke",
                "_type" : "house",
                "_id" : "5evNU3kB6bIFS3djvG3Y",
                "_score" : 0.2876821,
                "_source" : {
                  "price" : 2001,
                  "id" : 2001,
                  "title" : "asd"
                }
              }
            ]
          }
        }
        * */

    }
}
