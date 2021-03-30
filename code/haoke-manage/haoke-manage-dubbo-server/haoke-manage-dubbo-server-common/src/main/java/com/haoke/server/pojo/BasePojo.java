package com.haoke.server.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Auspice Tian
 * @time 2021-03-16 14:53
 * @current haoke-manage-com.haoke.dubbo.server.pojo
 */
@Data
public abstract class BasePojo implements Serializable {
    private Date created;
    private Date updated;
}

