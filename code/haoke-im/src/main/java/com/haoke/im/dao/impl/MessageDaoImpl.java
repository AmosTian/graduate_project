package com.haoke.im.dao.impl;

import com.haoke.im.dao.MessageDao;
import com.haoke.im.pojo.Message;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * @author Auspice Tian
 * @time 2021-04-02 18:17
 * @current haoke-im-com.haoke.dao.impl
 */
@Component
public class MessageDaoImpl implements MessageDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /*
    * 查询点对点消息记录，双向通信，A->B与B->A都要查询
    * 1.设置查询
    * 2.分页
    * */
    @Override
    public List<Message> findListByFromAndTo(Long fromId, Long toId, Integer page, Integer rows,Integer flag) {

        //A->B的消息
        Criteria fromList = Criteria.where("from.id").is(fromId).and("to.id").is(toId);
        //B->A的消息
        Criteria toList = Criteria.where("from.id").is(toId).and("to.id").is(fromId);
        Criteria criteria = new Criteria().orOperator(fromList,toList);

        PageRequest pageRequest;
        //实现分页
        if(flag<0){//如果小于0则倒序，其他情况逆序
             pageRequest = PageRequest.of(page-1,rows, Sort.by(Sort.Direction.DESC,"send_date"));
        }else{
            pageRequest = PageRequest.of(page-1,rows, Sort.by(Sort.Direction.ASC,"send_date"));
        }

        Query query = Query.query(criteria).with(pageRequest);

        return this.mongoTemplate.find(query,Message.class);
    }

    @Override
    public Message findMessageById(String id) {
        return this.mongoTemplate.findById(new ObjectId(id),Message.class);
    }

    @Override
    public UpdateResult updateMessageState(ObjectId id, Integer status) {
        Query query = Query.query(Criteria.where("id").is(id));

        Update update = Update.update("status",status);
        if(status.intValue() == 1){
            update.set("send_date",new Date());
        }else if(status.intValue() == 2){
            update.set("read_state",new Date());
        }
        return this.mongoTemplate.updateFirst(query,update,Message.class);
    }

    @Override
    public Message saveMessage(Message message) {
        message.setId(ObjectId.get());//若id不自行设置，则由mongo自动生成
        message.setSendDate(new Date());
        message.setStatus(1);
        return this.mongoTemplate.save(message);
    }

    @Override
    public DeleteResult deleteMessage(String id) {
        Query query = Query.query(Criteria.where("id").is(id));

        return this.mongoTemplate.remove(query,Message.class);
    }
}
