package com.haoke.api.controller;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.haoke.api.service.ESSearchService;
import com.haoke.api.vo.ESSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Auspice Tian
 * @time 2021-05-11 16:54
 * @current haoke-manage-com.haoke.api.controller.frontController
 */

@RequestMapping("housing/search")
@RestController
@CrossOrigin
public class ESController {
    @Autowired
    private ESSearchService esSearchService;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(ESController.class);

    @GetMapping
    public ESSearchResult search(@RequestParam("keyWord") String keyWord,
                                 @RequestParam(value = "page", defaultValue = "1") Integer page){

        if(page > 100){
            page = 1;//防止爬取过多数据
        }

        ESSearchResult search = this.esSearchService.search(keyWord, page);

        String redisKey = "HAOKE_HOT_WORD";

        if(search.getTotalPage() <= 1){
            //需要查询热词，按照得分，获取前5条
            Set<String> set = this.redisTemplate.opsForZSet().reverseRange(redisKey,0,4);
            search.setHotWord(set);
        }

        //处理热词
        Integer count = (Math.max(search.getTotalPage(),1) - 1)*esSearchService.ROWS +search.getList().size();
        //采用zset方式进行存储。值是的数量计算的得分
        this.redisTemplate.opsForZSet().add(redisKey,keyWord,count);

        LOGGER.info("[Search]搜索关键字为：" + keyWord + "，结果数量为：" + count);

        return search;
    }


}
