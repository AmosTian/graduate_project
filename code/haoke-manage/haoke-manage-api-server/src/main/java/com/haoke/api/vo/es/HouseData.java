package com.haoke.api.vo.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author Auspice Tian
 * @time 2021-05-11 16:37
 * @current haoke-manage-com.haoke.api.vo.es
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "haoke",type = "house",createIndex = false)
public class HouseData {
    @Id
    private String id;
    private String title;
    private String rent;
    private String floor;
    private String pic;
    private String orientation;
    private String houseType;
    private String rentMethod;
    private String time;
}
