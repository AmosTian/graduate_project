package com.haoke.im.controller;

import com.haoke.im.pojo.Message;
import com.haoke.im.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-04-03 9:31
 * @current haoke-im-com.haoke.im.controller
 */
@RestController
@RequestMapping("message")
@CrossOrigin
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 拉取消息列表
     * @param fromId
     * @param toId
     * @param page
     * @param rows
     * @return
     */
    @GetMapping
    public List<Message> queryMessageList(@RequestParam("fromId") Long fromId,
                                          @RequestParam("toId") Long toId,
                                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                                          @RequestParam(value = "rows",defaultValue = "10") Integer rows
                                          ){
        return this.messageService.queryMessageList(fromId, toId, page, rows,1);
    }
}
