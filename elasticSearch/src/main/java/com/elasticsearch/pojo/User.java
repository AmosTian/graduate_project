package com.elasticsearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author Auspice Tian
 * @time 2021-05-10 14:11
 * @current elasticSearch-com.elasticsearch.pojo
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "test",type = "user",shards = 6,replicas = 1)
public class User {
    @Id
    Long id;

    @Field(store = true)
    private String name;
    @Field
    private Integer age;
    @Field
    private String hobby;

}
