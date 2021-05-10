package com.es.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author Auspice Tian
 * @time 2021-05-10 13:02
 * @current es-restapi-com.es.pojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "test",shards = 6,replicas = 1,type = "user")
public class User {
    @Id
    private Long id;
    @Field(store = true)
    private String name;
    @Field
    private Integer age;
    @Field
    private String hobby;
}
