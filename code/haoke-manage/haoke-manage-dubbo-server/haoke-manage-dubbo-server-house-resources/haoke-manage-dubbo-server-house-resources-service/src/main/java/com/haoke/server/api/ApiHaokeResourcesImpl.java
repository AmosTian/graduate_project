package com.haoke.server.api;

import com.alibaba.dubbo.config.annotation.Service;
import com.haoke.server.pojo.HouseResources;
import com.haoke.server.service.HouseResourcesService;
import com.haoke.server.vo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Auspice Tian
 * @time 2021-03-16 21:16
 * @current haoke-manage-com.haoke.server.api
 */

//实现Dubbo，对外暴露服务
@Service(version = "${dubbo.service.version}")
public class ApiHaokeResourcesImpl implements ApiHouseResourcesService{

    @Autowired
    private HouseResourcesService houseResourcesService;

    @Override
    public int saveHouseResources(HouseResources houseResources) {
        return this.houseResourcesService.saveHouseResources(houseResources);
    }

    @Override
    public PageInfo<HouseResources> queryHouseResourcesList(int page, int pageSize, HouseResources queryCondition) {
        return this.houseResourcesService.queryHouseResourcesList(page, pageSize, queryCondition);
    }

    @Override
    public HouseResources queryHouseResourcesById(Long id) {
        return houseResourcesService.queryHouseResourcesById(id);
    }

    /**
     * 修改房源
     *
     * @param houseResources
     * @return
     */
    @Override
    public boolean updateHouseResources(HouseResources houseResources) {
        return this.houseResourcesService.updateHouseResources(houseResources);
    }
}
