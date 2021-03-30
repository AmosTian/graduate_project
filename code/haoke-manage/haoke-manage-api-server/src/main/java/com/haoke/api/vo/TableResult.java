package com.haoke.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-03-19 23:02
 * @current haoke-manage-com.haoke.api.vo
 */

@Data
@AllArgsConstructor
public class TableResult<T> {
    private List<T> list;
    private Pagination pagination;
}
