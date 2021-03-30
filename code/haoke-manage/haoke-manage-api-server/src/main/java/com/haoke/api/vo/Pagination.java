package com.haoke.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Auspice Tian
 * @time 2021-03-19 23:03
 * @current haoke-manage-com.haoke.api.vo
 */
@Data
@AllArgsConstructor
public class Pagination {
    private Integer current;
    private Integer pageSize;
    private Integer total;
}
