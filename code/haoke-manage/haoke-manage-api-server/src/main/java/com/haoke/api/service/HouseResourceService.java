package com.haoke.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.haoke.api.vo.Pagination;
import com.haoke.api.vo.TableResult;
import com.haoke.api.vo.bgVO.BGTableResult;
import com.haoke.server.api.ApiHouseResourcesService;
import com.haoke.server.pojo.HouseResources;
import com.haoke.server.vo.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-03-16 21:56
 * @current haoke-manage-com.haoke.api.service
 */

@Service//服务消费方
public class HouseResourceService {

    @Reference(version = "${dubbo.service.version}")
    private ApiHouseResourcesService apiHouseResourcesService;

    public boolean save(HouseResources houseResources){
        int result = this.apiHouseResourcesService.saveHouseResources(houseResources);

        return result==1;
    }

    public TableResult queryList(HouseResources houseResources, Integer currentPage, Integer pageSize) {
        PageInfo<HouseResources> pageInfo
                = this.apiHouseResourcesService.queryHouseResourcesList(currentPage, pageSize, houseResources);

        return new TableResult(pageInfo.getRecords(), new Pagination(currentPage, pageSize, pageInfo.getTotal()));
    }

    public BGTableResult querybgList(HouseResources houseResources, Integer currentPage, Integer pageSize){
        PageInfo<HouseResources> pageInfo
                = this.apiHouseResourcesService.queryHouseResourcesList(currentPage, pageSize, houseResources);

        return new BGTableResult(
                "0",
                "success",
                pageInfo.getTotal(),
                pageInfo.getRecords());
    }

    /*
    * 根据id查询房源数据
    *
    * @Param id
    * @Return
    * */
    public HouseResources queryHouseResourcesById(Long id){
        //调用dubbo服务查询数据

        return this.apiHouseResourcesService.queryHouseResourcesById(id);
    }

    public boolean update(HouseResources houseResources) {
        return this.apiHouseResourcesService.updateHouseResources(houseResources);
    }

    public BGTableResult querybgAllList(HouseResources houseResources) {
        List<HouseResources> list = this.apiHouseResourcesService.queryHouseResourcesAllList(houseResources);

        return new BGTableResult(
                "0",
                "success",
                list.size(),
                list);
    }

    public int delete(HouseResources houseResources) {
        return this.apiHouseResourcesService.deleteHouseResourcesById(houseResources.getId());
    }
}
