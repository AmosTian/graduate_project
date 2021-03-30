package com.haoke.api.graphql.myDataFetcherImpl;

import com.haoke.api.graphql.MyDataFetcher;
import com.haoke.api.service.HouseResourceService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Auspice Tian
 * @time 2021-03-22 12:13
 * @current haoke-manage-com.haoke.api.graphql.myDataFetcherImpl
 */
@Component
public class HouseResourcesDataFetcher implements MyDataFetcher {
    @Autowired
    HouseResourceService houseResourceService;

    @Override
    public String fieldName() {
        return "HouseResources";
    }

    @Override
    public Object dataFetcher(DataFetchingEnvironment environment) {
        Long id = Long.parseLong(environment.getArgument("id"));

        return this.houseResourceService.queryHouseResourcesById(id);
    }
}
