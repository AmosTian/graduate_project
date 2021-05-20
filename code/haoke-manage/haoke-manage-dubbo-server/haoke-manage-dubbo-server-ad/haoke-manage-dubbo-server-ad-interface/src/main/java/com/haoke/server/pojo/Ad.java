package com.haoke.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Auspice Tian
 * @time 2021-03-25 11:54
 * @current haoke-manage-com.haoke.server.pojo
 */

@Data
@TableName("TB_AD")
public class Ad extends BasePojo{
    private static final long serialVersionUID = -493439243433085768L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //广告类型
    private Integer type;
    //描述
    private String title;
    //'图片URL地址
    private String url;
}
