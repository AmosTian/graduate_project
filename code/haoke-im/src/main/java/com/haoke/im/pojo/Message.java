package com.haoke.im.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author Auspice Tian
 * @time 2021-04-02 18:04
 * @current haoke-im-com.haoke.pojo
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "message")//Mongo默认会将实体名作为集合名
public class Message {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    /*
    * 消息
    * */
    private String msg;
    /*
    * 消息状态 1-未读 ，2-已读
    * */
    @Indexed
    private Integer status;

    /*
    * 发送时间
    * */
    @Indexed
    @Field("send_date")
    private Date sendDate;

    /*
    * 读取时间
    * */
    @Field("read_date")
    private Date readDate;

    /*
    * 发送方
    * */
    private User from;

    /*
    * 接收方
    * */
    private User to;
}
