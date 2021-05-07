package com.haoke.server.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.haoke.server.pojo.HouseResources;
import com.haoke.server.service.BaseServiceImpl;
import com.haoke.server.service.HouseResourcesService;
import com.haoke.server.vo.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-03-16 21:03
 * @current haoke-manage-com.haoke.server.service.impl
 */

@Transactional//这是Spring的服务
@Service//开启事务
public class HouseResourcesServiceImpl
        extends BaseServiceImpl
        implements HouseResourcesService {
    @Override
    public int saveHouseResources(HouseResources houseResources) {
        // 编写校验逻辑，如果校验不通过，返回-1
        if (StringUtils.isBlank(houseResources.getTitle())) {
            return -1;
        }

        //其他校验以及逻辑省略 ……

        return super.save(houseResources);
    }

    @Override
    public int deleteHouseResourcesById(Long id) {
        return super.deleteById(id);
    }

    @Override
    public PageInfo<HouseResources> queryHouseResourcesList(int page, int pageSize, HouseResources queryCondition) {
        QueryWrapper<HouseResources> queryWrapper = new QueryWrapper<HouseResources>(queryCondition);
        queryWrapper.orderByDesc("updated");//按更新时间降序排列

        IPage iPage = super.queryPageList(queryWrapper, page, pageSize);
        return new PageInfo<HouseResources>(Long.valueOf(iPage.getTotal()).intValue() , page, pageSize, iPage.getRecords());
    }

    @Override
    public HouseResources queryHouseResourcesById(Long id) {
        return (HouseResources) super.queryById(id);
    }

    @Override
    public boolean updateHouseResources(HouseResources houseResources) {
        return super.update(houseResources)==1;
    }

    @Override
    public List<HouseResources> queryHouseResourcesAllList(HouseResources queryCondition) {
        QueryWrapper<HouseResources> queryWrapper =  new QueryWrapper<HouseResources>(queryCondition);
        queryWrapper.orderByDesc("updated");//按更新时间降序排列


        return this.queryAll(queryWrapper);
    }


}