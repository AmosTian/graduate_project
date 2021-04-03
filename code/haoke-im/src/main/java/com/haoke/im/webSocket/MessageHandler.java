package com.haoke.im.webSocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haoke.im.dao.MessageDao;
import com.haoke.im.pojo.Message;
import com.haoke.im.pojo.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Auspice Tian
 * @time 2021-04-02 20:29
 * @current haoke-im-com.haoke.im.webSocket
 */
@Component
public class MessageHandler extends TextWebSocketHandler {

    @Autowired
    private MessageDao messageDao;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /*
    * 记录已经登录的用户id的map
    * */
    private static final Map<Long,WebSocketSession> SESSIONS = new HashMap<>();

    /*
    * 连接建立，将用户的id加入到map中
    * */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long uid = (Long)session.getAttributes().get("uid");
        //将当前用户的session放入到map中，用于相应的session通信
        SESSIONS.put(uid,session);
    }


    /*
    * 处理message
    * 双方在线，则发送，并将读取状态改为已读
    * 若接收方不咋线，则不做处理
    * */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        //解析参数
        Long uid = (Long)session.getAttributes().get("uid");
        JsonNode jsonNode = MAPPER.readTree(textMessage.getPayload());
        Long toId = jsonNode.get("toId").asLong();
        String msg = jsonNode.get("msg").asText();

        //提取message
        Message message = Message.builder()
                .from(UserData.USER_MAP.get(uid))
                .to(UserData.USER_MAP.get(toId))
                .msg(msg)
                .build();

        // 将消息保存到MongoDB
        message = this.messageDao.saveMessage(message);

        // 判断to用户是否在线
        WebSocketSession toSession = SESSIONS.get(toId);
        if(toSession != null && toSession.isOpen()){
            //TODO 具体格式需要和前端对接
            toSession.sendMessage(new TextMessage(MAPPER.writeValueAsString(message)));
            // 更新消息状态为已读
            this.messageDao.updateMessageState(message.getId(), 2);
        }
    }

    /*
    * 连接关闭，将用户的id从记录已登录用户的SESSION移除
    * */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        SESSIONS.remove(session.getAttributes().get("uid"));
    }
}
