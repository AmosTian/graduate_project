package com.haoke.api.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Auspice Tian
 * @time 2021-04-25 14:03
 * @current haoke-manage-com.haoke.api.controller
 */
@RequestMapping("wx")
@RestController
public class WxLoginController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String ,String > redisTemplate;

    @PostMapping("login")
    public Map<String,Object> wxLogin(@RequestBody HashMap<String, String> param){
        Map<String,Object> result = new HashMap<>();
        result.put("status","200");

        String appid = "wx47b56adca7411314";
        String secret = "2b68f0eb4c8ecfcbec4929f3eee5aee8";

        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid="+ appid +
                "&secret="+ secret +
                "&js_code="+param.get("code")+
                "&grant_type=authorization_code";

        String resData = this.restTemplate.getForObject(url,String.class);

        if(StringUtils.contains(resData,"errcode")){
            //登录失败
            result.put("status","500");
            result.put("msg","登录失败");

            return result;
        }

        String ticket = DigestUtils.md5Hex(resData);
        String redisKey = "WX_MINIPRO_LOGIN"+ticket;
        //保存七天
        this.redisTemplate.opsForValue().set(redisKey,resData, Duration.ofDays(7));
        result.put("data",ticket);

        return result;
    }
}
