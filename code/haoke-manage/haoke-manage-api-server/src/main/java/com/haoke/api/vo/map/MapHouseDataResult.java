package com.haoke.api.vo.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-04-07 21:59
 * @current haoke-manage-com.haoke.api.vo.map
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapHouseDataResult {
    private List<MapHouseXY> list;
}
