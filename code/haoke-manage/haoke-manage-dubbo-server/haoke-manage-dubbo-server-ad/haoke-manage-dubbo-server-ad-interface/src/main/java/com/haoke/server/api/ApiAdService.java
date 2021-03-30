package com.haoke.server.api;

import com.haoke.server.pojo.Ad;
import com.haoke.server.vo.PageInfo;

/**
 * @author Auspice Tian
 * @time 2021-03-25 11:55
 * @current haoke-manage-com.haoke.server.api
 */

public interface ApiAdService {
    /**
     * 分页查询广告数据
     *
     * @param type 广告类型
     * @param page 页数
     * @param pageSize 每页显示的数据条数
     * @return
     */
    PageInfo<Ad> queryAdList(Integer type, Integer page, Integer pageSize);
}
