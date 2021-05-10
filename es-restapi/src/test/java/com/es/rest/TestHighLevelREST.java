package com.es.rest;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Auspice Tian
 * @time 2021-05-10 9:18
 * @current es-restapi-com.es.rest
 */
public class TestHighLevelREST {
    private RestHighLevelClient client;

    @Before
    public void init(){
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("8.140.130.91",9200,"http"),
                new HttpHost("8.140.130.91",9201,"http"),
                new HttpHost("8.140.130.91",9202,"http")
        );

        this.client = new RestHighLevelClient(builder);
    }

    @After
    public void after() throws IOException {
        this.client.close();
    }

    //测试新增数据
    @Test
    public void save() throws IOException {
        Map<String,Object> data = new HashMap<>();
        data.put("id", 2004);
        data.put("title", "南京西路 拎包入住 一室一厅");
        data.put("price", 2004);

        IndexRequest indexRequest = new IndexRequest("haoke","house").source(data);
        IndexResponse index = this.client.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println(index.getIndex());//haoke
        System.out.println(index.getId());//5uvjU3kB6bIFS3djB23k
        System.out.println(index.getType());//house
        System.out.println(index.getVersion());//1
        System.out.println(index.getResult());//CREATED
        System.out.println(index.getShardInfo());//ShardInfo{total=2, successful=2, failures=[]}
    }

    //新增文档异步操作
    @Test
    public void saveAsyncx() throws InterruptedException {
        Map<String,Object> data = new HashMap<>();
        data.put("id", 2005);
        data.put("title", "南京西路 拎包入住 一室一厅");
        data.put("price", 2005);

        IndexRequest indexRequest = new IndexRequest("haoke","house").source(data);

        this.client.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println(indexResponse.getIndex());//haoke
                System.out.println(indexResponse.getId());//6ev5U3kB6bIFS3djXG0t
                System.out.println(indexResponse.getType());//house
                System.out.println(indexResponse.getVersion());//1
                System.out.println(indexResponse.getResult());//CREATED
                System.out.println(indexResponse.getShardInfo());//ShardInfo{total=2, successful=2, failures=[]}
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println(e);
            }
        });


        Thread.sleep(20000);
    }


    //通过id查找数据
    @Test
    public void getById() throws IOException {
        GetRequest getRequest = new GetRequest("haoke","house","5evNU3kB6bIFS3djvG3Y");

        //返回指定的字段
        String[] includes = new String[]{"title","id"};
        String[] excludes = Strings.EMPTY_ARRAY;//排除空数据

        FetchSourceContext fetchSourceContext = new FetchSourceContext(true,includes,excludes);
        getRequest.fetchSourceContext(fetchSourceContext);

        GetResponse response = this.client.get(getRequest, RequestOptions.DEFAULT);

        System.out.println("数据->"+response.getSource());//数据->{id=2001, title=asd}
    }

    /**
     * 判断是否存在
     * @throws Exception
     */
    @Test
    public void testExists() throws Exception {
        GetRequest getRequest = new GetRequest("haoke", "house", "5OvNU3kB6bIFS3djOG3t");
        // 不返回的字段
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        boolean exists = this.client.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println("exists -> " + exists);//exists -> true
    }

    /**
     * 删除数据
     * @throws Exception
    */
    @Test
    public void testDelete() throws Exception {
        DeleteRequest deleteRequest = new DeleteRequest("haoke", "house", "5OvNU3kB6bIFS3djOG3t");
        DeleteResponse response = this.client.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());// OK or NOT_FOUND
    }

    /**
     * 更新数据
     * @throws Exception
     */
    @Test
    public void testUpdate() throws Exception {
        UpdateRequest updateRequest = new UpdateRequest("haoke", "house", "5evNU3kB6bIFS3djvG3Y");
        Map<String, Object> data = new HashMap<>();
        data.put("title", "张江高科2");
        data.put("price", "5000");

        updateRequest.doc(data);
        UpdateResponse response = this.client.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println("version -> " + response.getVersion());//version -> 2
    }

    /*
    * 测试搜索
    * */
    @Test
    public void search() throws IOException {
        SearchRequest searchRequest = new SearchRequest("haoke");
        searchRequest.types("house");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("title","拎包入住"));
        builder.from(0);
        builder.size(5);
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(builder);

        SearchResponse response = this.client.search(searchRequest, RequestOptions.DEFAULT);

        System.out.println("搜索到->"+response.getHits().totalHits+"条数据");
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
        /*
        *
        搜索到->4条数据
        {"price":2003,"id":2003,"title":"南京西路 拎包入住 一室一厅"}
        {"price":2004,"id":2004,"title":"南京西路 拎包入住 一室一厅"}
        {"price":4500,"id":2002,"title":"南京西路 拎包入住 一室一厅"}
        {"price":2005,"id":2005,"title":"南京西路 拎包入住 一室一厅"}
        * */
    }

}
