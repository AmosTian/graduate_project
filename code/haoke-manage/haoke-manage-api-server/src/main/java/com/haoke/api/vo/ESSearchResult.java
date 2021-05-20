package com.haoke.api.vo;

import com.haoke.api.vo.es.HouseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @author Auspice Tian
 * @time 2021-05-11 16:39
 * @current haoke-manage-com.haoke.api.vo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ESSearchResult {
    private Integer totalPage;
    private List<HouseData> list;
    private Set<String> hotWord;
}
