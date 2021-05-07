package com.haoke.api.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Auspice Tian
 * @time 2021-04-08 11:10
 * @current haoke-manage-com.haoke.api.pojo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "house")//指定表名称
@Builder
public class MongoHouse {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private String estate_id;
    private String title;
    private Float[] loc;
}
