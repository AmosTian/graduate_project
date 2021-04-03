package com.haoke.im.Interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author Auspice Tian
 * @time 2021-04-02 22:13
 * @current haoke-im-com.haoke.im.webSocket
 */
/*
* 消息拦截器
* */
@Component
public class MessageHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        String path = serverHttpRequest.getURI().getPath();//ws://127.0.0.1/ws/{uid}

        String[] ss = StringUtils.split(path, '/');

        if(ss.length != 2){
            //若请求格式不对，则拦截
            return false;
        }

        if(!StringUtils.isNumeric(ss[1])){
            //如果参数不是数字，则拦截
            return false;
        }

        map.put("uid",Long.valueOf(ss[1]));

        //将用户id放入session，放行
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
