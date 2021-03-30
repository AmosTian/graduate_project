package com.haoke.server.service;

import com.haoke.server.pojo.HouseResources;
import com.haoke.server.vo.PageInfo;

/**
 * @author Auspice Tian
 * @time 2021-03-16 18:49
 * @current haoke-manage-com.haoke.server.service
 */
public interface HouseResourcesService {
    /**
     *
     * @param houseResources
     * @return -1:输入的参数不符合要求，0：数据插入数据库失败，1：成功
     */
    int saveHouseResources(HouseResources houseResources);

    PageInfo<HouseResources> queryHouseResourcesList(int page, int pageSize, HouseResources queryCondition);

    /**
     * 根据房源id查询房源数据
     *
     * @param id
     * @return
     */
    public HouseResources queryHouseResourcesById(Long id);

    /**
     * 更新房源数据
     *
     * @param houseResources
     * @return
     */
    boolean updateHouseResources(HouseResources houseResources);
}
