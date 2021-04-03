package com.haoke.im;

import com.haoke.im.dao.MessageDao;
import com.haoke.im.pojo.Message;
import com.haoke.im.pojo.User;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-04-02 18:51
 * @current haoke-im-com.haoke.im
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestImDao {
    @Autowired
    private MessageDao messageDao;

    @Test
    public void testSave(){
        Message message = Message.builder()
                .id(ObjectId.get())
                .msg("你好")
                .sendDate(new Date())
                .status(1)
                .from(new User(1001L, "zhangsan"))
                .to(new User(1002L,"lisi"))
                .build();
        this.messageDao.saveMessage(message);

        message = Message.builder()
                .id(ObjectId.get())
                .msg("你也好")
                .sendDate(new Date())
                .status(1)
                .to(new User(1001L, "zhangsan"))
                .from(new User(1002L,"lisi"))
                .build();
        this.messageDao.saveMessage(message);
        message = Message.builder()
                .id(ObjectId.get())
                .msg("我在学习开发IM")
                .sendDate(new Date())
                .status(1)
                .from(new User(1001L, "zhangsan"))
                .to(new User(1002L,"lisi"))
                .build();
        this.messageDao.saveMessage(message);

        message = Message.builder()
                .id(ObjectId.get())
                .msg("那很好啊！")
                .sendDate(new Date())
                .status(1)
                .to(new User(1001L, "zhangsan"))
                .from(new User(1002L,"lisi"))
                .build();
        this.messageDao.saveMessage(message);
        System.out.println("ok");
    }


    @Test
    public void testQueryList(){
        List<Message> list = this.messageDao.findListByFromAndTo(1001L, 1002L, 1, 8,1);
        for (Message message : list) {
            System.out.println(message);
        }
    }

    @Test
    public void testQueryById(){
        Message message = this.messageDao.findMessageById("6066f94a33930e32ad8ac991");
        System.out.println(message);
    }
}
