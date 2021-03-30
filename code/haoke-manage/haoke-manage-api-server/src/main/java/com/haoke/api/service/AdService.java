package com.haoke.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.haoke.api.vo.WebResult;
import com.haoke.server.api.ApiAdService;
import com.haoke.server.pojo.Ad;
import com.haoke.server.vo.PageInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Auspice Tian
 * @time 2021-03-25 14:01
 * @current haoke-manage-com.haoke.api.service
 */
@Service
public class AdService {

    @Reference(version = "1.0.0")
    private ApiAdService apiAdService;

    public PageInfo<Ad> queryAdList(Integer type, Integer page, Integer pageSize) {

        return this.apiAdService.queryAdList(type, page, pageSize);
    }
}