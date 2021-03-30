package com.haoke.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.haoke.server.pojo.Ad;
import com.haoke.server.service.AdService;
import com.haoke.server.service.BaseServiceImpl;
import com.haoke.server.vo.PageInfo;
import org.springframework.stereotype.Service;

/**
 * @author Auspice Tian
 * @time 2021-03-25 12:07
 * @current haoke-manage-com.haoke.server.service.impl
 */

@Service
public class AdServiceImpl extends BaseServiceImpl implements AdService {
    @Override
    public PageInfo<Ad> queryAdList(Ad ad, Integer page, Integer pageSize) {
        QueryWrapper queryWrapper = new QueryWrapper();

        //排序
        queryWrapper.orderByDesc("updated");
        //按广告的类型查询
        queryWrapper.eq("type",ad.getType());

        IPage iPage = super.queryPageList(queryWrapper,page,pageSize);

        return new PageInfo<>(Long.valueOf(iPage.getTotal()).intValue(),page,pageSize,iPage.getRecords());
    }
}
