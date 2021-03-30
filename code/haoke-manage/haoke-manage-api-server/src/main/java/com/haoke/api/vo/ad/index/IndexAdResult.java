package com.haoke.api.vo.ad.index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-03-25 20:39
 * @current haoke-manage-com.haoke.api.vo.ad.index
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexAdResult {
    private List<IndexAdResultData> list;
}
