package com.haoke.api.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Auspice Tian
 * @time 2021-03-30 22:24
 * @current haoke-manage-com.haoke.api.interceptor
 */

@Component
public class RedisInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*
        * 对于mock的请求，讲解中出现错误，解决方案为放行所有的mock请求
        * 但我的问题是mock请求会被存入redis缓存，所以需要放行 请求参数带mock的 GET请求
        * */
        if(StringUtils.equalsIgnoreCase(request.getMethod(), "OPTIONS")){
            return true;
        }
        if(request.getRequestURI().startsWith("/mock")){
            return true;
        }

        //判断请求方式，PUT,DELETE,无需缓存,拦截器放行
        if(!StringUtils.equalsIgnoreCase(request.getMethod(),"GET")){
            if(!StringUtils.equalsIgnoreCase(request.getRequestURI(),"/graphql"))
                //POST请求有可能是GraphQL查询，也要做拦截，不放行
                return true;
        }

        //通过缓存做命中，查询redisKey
        String data = this.redisTemplate.opsForValue().get(createRedisKey(request));
        if(StringUtils.isEmpty(data)){
            //缓存未命中，则放行请求
            return true;
        }
        //若缓存命中，将data中的数据返回
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        // 支持跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Token");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.getWriter().write(data);

        return false;
    }

    public static String createRedisKey(HttpServletRequest request) throws IOException {
        String paramStr = request.getRequestURI();

        Map<String,String[]> parameterMap = request.getParameterMap();

        if(parameterMap.isEmpty()){
            //URI中没有参数，则是POST的GraphQL请求，需要从请求体中获取数据
            paramStr += IOUtils.toString(request.getInputStream(),"UTF-8");
        }else{
            paramStr += mapper.writeValueAsString(request.getParameterMap());
        }

        String redisKey = "WEB_DATA_" + DigestUtils.md5Hex(paramStr);

        return redisKey;
    }

}
