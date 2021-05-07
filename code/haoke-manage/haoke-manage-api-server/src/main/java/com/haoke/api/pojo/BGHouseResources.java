package com.haoke.api.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.haoke.server.pojo.HouseResources;
import lombok.Data;

/**
 * @author Auspice Tian
 * @time 2021-05-02 16:26
 * @current haoke-manage-com.haoke.api.pojo
 */

@Data
public class BGHouseResources extends HouseResources {
    /*
    * 地址信息
    * */
    private String estateId;

    /*
    * 经纬度
    * */
    private Float lng;
    private Float lat;
}
