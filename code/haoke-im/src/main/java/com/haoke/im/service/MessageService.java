package com.haoke.im.service;

import com.haoke.im.dao.MessageDao;
import com.haoke.im.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-04-03 9:32
 * @current haoke-im-com.haoke.im.service
 */

@Service
public class MessageService {

    @Autowired
    MessageDao messageDao;

    public List<Message> queryMessageList(Long fromId, Long toId, Integer page, Integer rows,Integer flag){
        List<Message> list = this.messageDao.findListByFromAndTo(fromId, toId, page, rows,flag);

        for (Message message : list) {
            if(message.getStatus().intValue() == 1){
                //修改消息状态为已读
                this.messageDao.updateMessageState(message.getId(),2);
            }
        }

        return list;
    }
}
