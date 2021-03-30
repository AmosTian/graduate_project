package com.haoke.server.service;

import com.haoke.server.pojo.Ad;
import com.haoke.server.vo.PageInfo;

/**
 * @author Auspice Tian
 * @time 2021-03-25 12:00
 * @current haoke-manage-com.haoke.server.service
 */
public interface AdService {

    PageInfo<Ad> queryAdList(Ad ad, Integer page, Integer pageSize);
}
