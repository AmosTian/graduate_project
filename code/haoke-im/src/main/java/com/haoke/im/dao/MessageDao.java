package com.haoke.im.dao;

import com.haoke.im.pojo.Message;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.bson.types.ObjectId;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-04-02 18:12
 * @current haoke-im-com.haoke.dao
 */
public interface MessageDao {

    /**
     * 查询点对点聊天记录
     * @param fromId
     * @param toId
     * @param page
     * @param rows
     * @param flag//排序方式
     * @return
     */
    List<Message> findListByFromAndTo(Long fromId,Long toId,Integer page,Integer rows, Integer flag);

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    Message findMessageById(String id);

    /**
     * 更新消息状态
     * @param id
     * @param status
     * @return
     */
    UpdateResult updateMessageState(ObjectId id, Integer status);

    /*
    * 新增消息数据
    * @param message
    * @return
    * */
    Message saveMessage(Message message);

    /**
     * 根据消息id删除数据
     * @param id
     * @return
     */
    DeleteResult deleteMessage(String id);
}
