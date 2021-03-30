package com.haoke.server.api;

import com.alibaba.dubbo.config.annotation.Service;
import com.haoke.server.pojo.Ad;
import com.haoke.server.service.AdService;
import com.haoke.server.vo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Auspice Tian
 * @time 2021-03-25 12:13
 * @current haoke-manage-com.haoke.server.api
 */
@Service(version = "${dubbo.service.version}")
public class ApiAdServiceImpl implements ApiAdService{
    @Autowired
    private AdService adService;

    @Override
    public PageInfo<Ad> queryAdList(Integer type, Integer page, Integer pageSize) {
        Ad ad = new Ad();
        ad.setType(type);

        return this.adService.queryAdList(ad,page,pageSize);
    }
}
