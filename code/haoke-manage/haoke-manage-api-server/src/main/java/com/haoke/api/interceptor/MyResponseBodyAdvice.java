package com.haoke.api.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haoke.api.controller.GraphQLController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.Duration;

/**
 * @author Auspice Tian
 * @time 2021-03-31 9:22
 * @current haoke-manage-com.haoke.api.interceptor
 */
@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice {

    @Autowired
    private RedisTemplate redisTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    /*
    * 选择要横切的响应
    * */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {

        /*
        * 对所有查询进行横切，
        * 如 GET请求，POST的GraphQLL
        * */
        if(returnType.hasMethodAnnotation(GetMapping.class)){
            return true;
        }
        if(returnType.hasMethodAnnotation(PostMapping.class) &&
                StringUtils.equals(GraphQLController.class.getName(),returnType.getExecutable().getDeclaringClass().getName())){
            return true;
        }

        return false;
    }

    /*
    * 横切通知
    * 将所有返回结果缓存
    * */
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        try {
            String redisKey = RedisInterceptor.createRedisKey(((ServletServerHttpRequest) request).getServletRequest());

            String redisValue;

            if(body instanceof String){
                redisValue = (String) body;
            }else{
                redisValue = mapper.writeValueAsString(body);
            }

            this.redisTemplate.opsForValue().set(redisKey,redisValue,Duration.ofHours(1));
        }catch (Exception e){
            e.getStackTrace();
        }

        return body;
    }
}
