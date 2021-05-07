package com.haoke.server.api;

import com.haoke.server.pojo.HouseResources;
import com.haoke.server.vo.PageInfo;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-03-16 17:01
 * @current haoke-manage-com.haoke.dubbo.server.api
 */
public interface ApiHouseResourcesService {

    /**
     * @param houseResources
     *
     * @return -1:输入的参数不符合要求，0：数据插入数据库失败，1：成功
     */
    int saveHouseResources(HouseResources houseResources);

    /**
     * @param id
     *
     * @return -1:输入的参数不符合要求，0：数据插入数据库失败，1：成功
     */
    int deleteHouseResourcesById(Long id);

    /**
     * 分页查询房源列表
     *
     * @param page 当前页
     * @param pageSize 页面大小
     * @param queryCondition 查询条件
     * @return
     */
    PageInfo<HouseResources> queryHouseResourcesList(int page, int pageSize, HouseResources queryCondition);

    /*
    * 实现通过id查询 房源
    *
    * @Param id 房源id
    * @return
    * */
    HouseResources queryHouseResourcesById(Long id);

    /**
     * 更新房源数据
     *
     * @param houseResources
     * @return
     */
    boolean updateHouseResources(HouseResources houseResources);

    /*
    * 查询所有房源信息
    * */
    List<HouseResources> queryHouseResourcesAllList(HouseResources queryCondition);
}
