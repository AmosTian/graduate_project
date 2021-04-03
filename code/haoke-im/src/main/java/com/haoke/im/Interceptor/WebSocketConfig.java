package com.haoke.im.Interceptor;

import com.haoke.im.webSocket.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author Auspice Tian
 * @time 2021-04-02 22:19
 * @current haoke-im-com.haoke.im.Interceptor
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private MessageHandler messageHandler;

    @Autowired
    private MessageHandshakeInterceptor handshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(messageHandler,"/ws/{uid}")
                .setAllowedOrigins("*")//支持跨域
                .addInterceptors(handshakeInterceptor);//添加拦截器
    }
}
