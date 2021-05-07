package com.haoke.api.controller.frontController;

import com.haoke.api.service.AdService;
import com.haoke.api.vo.WebResult;
import com.haoke.server.pojo.Ad;
import com.haoke.server.vo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Auspice Tian
 * @time 2021-03-25 13:51
 * @current haoke-manage-com.haoke.api.controller
 */
@RequestMapping("ad")
@RestController
@CrossOrigin//允许跨域
public class AdController {
    @Autowired
    private AdService adService;

    /**
     * 首页广告位
     * @return
     */
    @GetMapping
    public WebResult queryIndexad(){
        PageInfo<Ad> pageInfo = this.adService.queryAdList(1,1,3);

        List<Ad> ads = pageInfo.getRecords();
        List<Map<String,Object>> data = new ArrayList<>();
        for (Ad ad : ads) {
            Map<String,Object> map = new HashMap<>();

            map.put("original",ad.getUrl());
            data.add(map);
        }

        return WebResult.ok(data);
    }
}
