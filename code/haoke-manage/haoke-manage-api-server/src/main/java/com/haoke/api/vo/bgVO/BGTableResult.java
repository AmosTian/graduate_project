package com.haoke.api.vo.bgVO;

import com.haoke.api.vo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-04-29 18:46
 * @current haoke-manage-com.haoke.api.vo.bgVO
 */
@Data
@AllArgsConstructor
public class BGTableResult<T> {
    private String code;
    private String msg;
    private Integer count;
    private List<T> data;
}
