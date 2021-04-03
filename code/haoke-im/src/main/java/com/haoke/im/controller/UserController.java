package com.haoke.im.controller;

import com.haoke.im.pojo.Message;
import com.haoke.im.pojo.User;
import com.haoke.im.pojo.UserData;
import com.haoke.im.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Auspice Tian
 * @time 2021-04-03 9:52
 * @current haoke-im-com.haoke.im.controller
 */
@RequestMapping("user")
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private MessageService messageService;

    //拉取用户列表
    @GetMapping
    public List<Map<String,Object>> queryUserList(@RequestParam("fromId")Long fromId){
        List<Map<String,Object>> result = new ArrayList<>();

        //找出跟当前用户有关的所有message
        for(Map.Entry<Long, User> user : UserData.USER_MAP.entrySet()) {
           //排除当前用户
            Long id = user.getValue().getId();
            if(id.equals(fromId))
                continue;
            Map<String,Object> map = new HashMap<>();
            map.put("id",id);
            map.put("avatar","https://haoke-1257323542.cos.ap-beijing.myqcloud.com/mock-data/avatar.png");
            map.put("from_user", fromId);
            map.put("info_type", null);
            map.put("to_user", id);
            map.put("username", user.getValue().getUsername());

            // 获取最新一条消息
            List<Message> messages = this.messageService.queryMessageList(fromId, user.getValue().getId(), 1, 1,-1);
            if (messages != null && !messages.isEmpty()) {
                Message message = messages.get(0);
                map.put("chat_msg", message.getMsg());
                map.put("chat_time", message.getSendDate().getTime());
            }

            result.add(map);
        }

        return result;
    }

}
